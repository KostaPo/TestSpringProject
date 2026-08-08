package com.example.testproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "request_log")
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "request_time", nullable = false)
    private OffsetDateTime requestTime;

    @Column(name = "endpoint", nullable = false)
    private String endpoint;

    public RequestLog(
            String userId,
            OffsetDateTime requestTime,
            String endpoint
    ) {
        this.userId = userId;
        this.requestTime = requestTime;
        this.endpoint = endpoint;
    }
}