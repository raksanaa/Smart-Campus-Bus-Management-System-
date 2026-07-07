package com.campustracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campustracker.model.TripLog;
import com.campustracker.service.TripLogService;

@CrossOrigin(origins = "http://127.0.0.1:5500") // allow frontend requests
@RestController
@RequestMapping("/api/trips")


public class TripLogController {

    @Autowired
    private TripLogService tripLogService;

    /**
     * Endpoint to add a new boarding, alighting, or status update log to a specific trip.
     * The tripId is taken from the path, and the log details are in the request body.
     * * URL: POST /api/trips/{tripId}/logs
     */
    @PostMapping("/{tripId}/logs")
    public ResponseEntity<TripLog> addLogToTrip(
            @PathVariable Long tripId, 
            @RequestBody TripLog log) {
        
        // Calls the service method, passing the path variable (tripId) and the request body (log)
        TripLog savedLog = tripLogService.addLogToTrip(tripId, log);
        return new ResponseEntity<>(savedLog, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve all logs for a specific trip.
     * * URL: GET /api/trips/{tripId}/logs
     */
    @GetMapping("/{tripId}/logs")
    public ResponseEntity<List<TripLog>> getLogsByTripId(@PathVariable Long tripId) {
        List<TripLog> logs = tripLogService.getLogsByTripId(tripId);
        return ResponseEntity.ok(logs);
    }
}
