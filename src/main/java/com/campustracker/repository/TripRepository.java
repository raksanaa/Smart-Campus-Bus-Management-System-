package com.campustracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campustracker.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByShuttleId(Long shuttleId);
   List<Trip> findByCurrentStatusIgnoreCase(String status);

}


