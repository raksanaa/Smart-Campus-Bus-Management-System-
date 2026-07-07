package com.campustracker.controller;

import java.util.List;
import java.util.Map;

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

import com.campustracker.model.Trip;
import com.campustracker.service.TripService;

@CrossOrigin(origins = "http://127.0.0.1:5500") // ✅ matches your frontend
@RestController
@RequestMapping("/api")
public class TripController {

    @Autowired
    private TripService tripService;

    // ✅ 1️⃣ GET all trips — used by Trip Log Manager in frontend
    @GetMapping("/trips")
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    // ✅ 2️⃣ POST new trip — used when adding/scheduling a trip
    @PostMapping("/trips")
    public ResponseEntity<Trip> scheduleTrip(@RequestBody Trip trip) {
        Trip scheduledTrip = tripService.scheduleTrip(trip);
        return new ResponseEntity<>(scheduledTrip, HttpStatus.CREATED);
    }

    // ✅ 3️⃣ GET trips by shuttle — used for specific shuttle view
    @GetMapping("/trips/shuttle/{shuttleId}")
    public ResponseEntity<List<Trip>> getTripsByShuttle(@PathVariable Long shuttleId) {
        List<Trip> trips = tripService.getTripsByShuttleId(shuttleId);
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

   

    // ✅ 5️⃣ GET shuttle usage — placeholder (optional analytics)
    @GetMapping("/reports/shuttle-usage")
    public ResponseEntity<String> getShuttleUsageReport() {
        return new ResponseEntity<>("Shuttle utilization report placeholder. Implement aggregation logic here.", HttpStatus.OK);
    }

    @GetMapping("/reports/grouped-trips")
public ResponseEntity<Map<String, List<Trip>>> getGroupedTrips() {
    Map<String, List<Trip>> groupedTrips = tripService.getTripsByStatus();
    return new ResponseEntity<>(groupedTrips, HttpStatus.OK);
}

}
