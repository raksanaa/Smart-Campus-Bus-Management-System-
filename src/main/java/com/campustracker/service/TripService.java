package com.campustracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campustracker.model.Trip;
import com.campustracker.repository.TripRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    public Trip scheduleTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public List<Trip> getTripsByShuttleId(Long shuttleId) {
        return tripRepository.findByShuttleId(shuttleId);
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    // ✅ New method to group trips by their status
    public Map<String, List<Trip>> getTripsByStatus() {
        Map<String, List<Trip>> groupedTrips = new HashMap<>();
        groupedTrips.put("ongoing", tripRepository.findByCurrentStatusIgnoreCase("Ongoing"));
        groupedTrips.put("scheduled", tripRepository.findByCurrentStatusIgnoreCase("Scheduled"));
        groupedTrips.put("active", tripRepository.findByCurrentStatusIgnoreCase("Active"));
        return groupedTrips;
    }
}
