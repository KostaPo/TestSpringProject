package com.example.testproject;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TimeServiceTest {

    private final TimeService service = new TimeService();

    @Test
    void shouldReturnMoscowTime() {

        Map<String, String> result = service.getCurrentTime();

        assertNotNull(result);
        assertTrue(result.containsKey("time"));

        String timeValue = result.get("time");
        assertNotNull(timeValue);

        OffsetDateTime parsedTime = OffsetDateTime.parse(timeValue);

        assertEquals(
                ZoneOffset.ofHours(3),
                parsedTime.getOffset()
        );
    }
}