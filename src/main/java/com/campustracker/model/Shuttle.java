package com.campustracker.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "shuttles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shuttle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shuttleId;

    @Column(name = "shuttle_number", nullable = false, unique = true, length = 20)
    private String shuttleNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false, length = 100)
    private String route;

    @Column(nullable = false, length = 20)
    private String status; // Active / Maintenance / Offline

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
