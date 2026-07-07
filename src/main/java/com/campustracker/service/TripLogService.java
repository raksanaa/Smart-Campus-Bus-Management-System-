package com.campustracker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campustracker.model.Trip;
import com.campustracker.model.TripLog;
import com.campustracker.model.User;
import com.campustracker.repository.TripLogRepository;
import com.campustracker.repository.TripRepository;
import com.campustracker.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TripLogService {

    @Autowired
    private TripLogRepository tripLogRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TripLog> getLogsByTripId(Long tripId) {
        return tripLogRepository.findByTripTripId(tripId);
    }

    @Transactional
    public TripLog addLogToTrip(Long tripId, TripLog newLog) {

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with ID: " + tripId));
        newLog.setTrip(trip);

        if (newLog.getUser() != null && newLog.getUser().getUserId() != null) {
            User user = userRepository.findById(newLog.getUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + newLog.getUser().getUserId()));
            newLog.setUser(user);
        }

        newLog.setTimestamp(LocalDateTime.now());
        TripLog savedLog = tripLogRepository.save(newLog);

        String action = newLog.getAction();

        if ("Boarding".equalsIgnoreCase(action) && "Scheduled".equalsIgnoreCase(trip.getCurrentStatus())) {
            trip.setCurrentStatus("Ongoing");
            tripRepository.save(trip);
        } else if ("Completed".equalsIgnoreCase(action)) {
            trip.setCurrentStatus("Completed");
            tripRepository.save(trip);
        }

        return savedLog;
    }
}
