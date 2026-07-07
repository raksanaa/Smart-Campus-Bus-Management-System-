package com.campustracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campustracker.model.TripLog;

@Repository
public interface TripLogRepository extends JpaRepository<TripLog, Long> {
    List<TripLog> findByTripTripId(Long tripId);
}
