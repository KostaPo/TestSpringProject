// =============================================================
// LlmAgentReadOnlyRoleTest.java
//
// Проверяет, что после выполнения init-скрипта
// (V1__init_llm_agent_readonly.sql) роль llm_agent:
//   1. существует и является членом pg_read_all_data
//   2. может делать SELECT (в т.ч. по таблицам, созданным
//      уже ПОСЛЕ старта контейнера — проверка default-доступа)
//   3. НЕ может делать INSERT/UPDATE/DELETE
//   4. НЕ может создавать объекты (CREATE TABLE)

// Файл V1__init_llm_agent_readonly.sql должен лежать в
// src/test/resources/init/V1__init_llm_agent_readonly.sql
// =============================================================

package com.example.testproject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class LlmAgentReadOnlyRoleTest {

    private static final Logger log = LoggerFactory.getLogger(LlmAgentReadOnlyRoleTest.class);

    private static final String ADMIN_USER = "timeapp";
    private static final String ADMIN_PASSWORD = "timeapp_pass";
    private static final String DB_NAME = "timeapp";

    private static final String AGENT_USER = "llm_agent";
    // должен совпадать с паролем в init-скрипте
    private static final String AGENT_PASSWORD = "llm_agent_password";

    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16")
                    .withUsername(ADMIN_USER)
                    .withPassword(ADMIN_PASSWORD)
                    .withDatabaseName(DB_NAME)
                    .withCopyFileToContainer(
                            MountableFile.forClasspathResource("init/V1__init_llm_agent_readonly.sql"),
                            "/docker-entrypoint-initdb.d/V1__init_llm_agent_readonly.sql"
                    );

    @BeforeAll
    static void setUpSchemaAndData() throws SQLException {
        log.info("Контейнер поднят, JDBC URL: {}", POSTGRES.getJdbcUrl());
        log.info("Создаю тестовую схему и таблицу от имени администратора ({})", ADMIN_USER);

        try (Connection conn = adminConnection();
             Statement st = conn.createStatement()) {
            st.execute("CREATE SCHEMA IF NOT EXISTS demo_schema");
            st.execute("CREATE TABLE demo_schema.demo(id int, name text)");
            st.execute("INSERT INTO demo_schema.demo VALUES (1, 'hello')");
        }
        log.info("Тестовые данные готовы: demo_schema.demo(1, 'hello')");
    }

    @Test
    void llmAgentRoleExistsAndIsMemberOfPgReadAllData() throws SQLException {
        log.info("Проверяю членство роли '{}' в pg_read_all_data", AGENT_USER);

        try (Connection conn = adminConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT r.rolname " +
                             "FROM pg_auth_members m " +
                             "JOIN pg_roles r ON r.oid = m.roleid " +
                             "JOIN pg_roles u ON u.oid = m.member " +
                             "WHERE u.rolname = ?");
        ) {
            ps.setString(1, AGENT_USER);
            ResultSet rs = ps.executeQuery();

            boolean isMember = false;
            while (rs.next()) {
                String memberOf = rs.getString("rolname");
                log.debug("{} является членом роли: {}", AGENT_USER, memberOf);
                if ("pg_read_all_data".equals(memberOf)) {
                    isMember = true;
                }
            }
            log.info("Результат: {} состоит в pg_read_all_data = {}", AGENT_USER, isMember);
            assertTrue(isMember, "llm_agent должен быть членом pg_read_all_data");
        }
    }

    @Test
    void llmAgentRoleHasNoElevatedAttributes() throws SQLException {
        log.info("Проверяю, что у роли '{}' нет повышенных привилегий", AGENT_USER);

        try (Connection conn = adminConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT rolsuper, rolcreatedb, rolcreaterole, rolbypassrls " +
                             "FROM pg_roles WHERE rolname = ?")
        ) {
            ps.setString(1, AGENT_USER);
            ResultSet rs = ps.executeQuery();
            assertTrue(rs.next(), "роль llm_agent должна существовать");

            boolean isSuper = rs.getBoolean("rolsuper");
            boolean canCreateDb = rs.getBoolean("rolcreatedb");
            boolean canCreateRole = rs.getBoolean("rolcreaterole");
            boolean bypassRls = rs.getBoolean("rolbypassrls");

            log.info("rolsuper={}, rolcreatedb={}, rolcreaterole={}, rolbypassrls={}",
                    isSuper, canCreateDb, canCreateRole, bypassRls);

            assertFalse(isSuper, "не должен быть superuser");
            assertFalse(canCreateDb, "не должен уметь создавать БД");
            assertFalse(canCreateRole, "не должен уметь создавать роли");
            assertFalse(bypassRls, "не должен обходить RLS");
        }
    }

    @Test
    void llmAgentCanSelectExistingAndNewData() throws SQLException {
        log.info("Проверяю SELECT от имени '{}' по данным, созданным после инициализации", AGENT_USER);

        try (Connection conn = agentConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT name FROM demo_schema.demo WHERE id = 1")) {

            assertTrue(rs.next(), "должна вернуться хотя бы одна строка");
            String name = rs.getString("name");
            log.info("SELECT прошёл успешно, получено значение: '{}'", name);
            assertEquals("hello", name);
        }
    }

    @Test
    void llmAgentCannotInsert() throws SQLException {
        log.info("Проверяю, что INSERT от имени '{}' запрещён", AGENT_USER);

        try (Connection conn = agentConnection();
             Statement st = conn.createStatement()) {

            SQLException ex = assertThrows(SQLException.class, () ->
                    st.execute("INSERT INTO demo_schema.demo VALUES (2, 'not_allowed')"));

            log.info("INSERT корректно отклонён, сообщение БД: {}", ex.getMessage());
            assertTrue(ex.getMessage().toLowerCase().contains("permission denied"),
                    "ожидали permission denied, получили: " + ex.getMessage());
        }
    }

    @Test
    void llmAgentCannotUpdateOrDelete() throws SQLException {
        log.info("Проверяю, что UPDATE от имени '{}' запрещён", AGENT_USER);
        try (Connection conn = agentConnection();
             Statement st = conn.createStatement()) {

            SQLException ex = assertThrows(SQLException.class, () ->
                    st.execute("UPDATE demo_schema.demo SET name = 'x' WHERE id = 1"));
            log.info("UPDATE корректно отклонён, сообщение БД: {}", ex.getMessage());
        }

        log.info("Проверяю, что DELETE от имени '{}' запрещён", AGENT_USER);
        try (Connection conn = agentConnection();
             Statement st = conn.createStatement()) {

            SQLException ex = assertThrows(SQLException.class, () ->
                    st.execute("DELETE FROM demo_schema.demo WHERE id = 1"));
            log.info("DELETE корректно отклонён, сообщение БД: {}", ex.getMessage());
        }
    }

    @Test
    void llmAgentCannotCreateTable() throws SQLException {
        log.info("Проверяю, что CREATE TABLE от имени '{}' запрещён", AGENT_USER);

        try (Connection conn = agentConnection();
             Statement st = conn.createStatement()) {

            SQLException ex = assertThrows(SQLException.class, () ->
                    st.execute("CREATE TABLE public.should_fail(id int)"));

            log.info("CREATE TABLE корректно отклонён, сообщение БД: {}", ex.getMessage());
            assertTrue(ex.getMessage().toLowerCase().contains("permission denied"),
                    "ожидали permission denied, получили: " + ex.getMessage());
        }
    }

    private static Connection adminConnection() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), ADMIN_USER, ADMIN_PASSWORD);
    }

    private static Connection agentConnection() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), AGENT_USER, AGENT_PASSWORD);
    }
}