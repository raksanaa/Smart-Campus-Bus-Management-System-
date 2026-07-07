package com.campustracker.controller;

import com.campustracker.model.Shuttle;
import com.campustracker.service.ShuttleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shuttles")
public class ShuttleController {

    @Autowired
    private ShuttleService shuttleService;

    // Endpoint: /api/shuttles (POST) - Add a new shuttle
    @PostMapping
    public ResponseEntity<Shuttle> addShuttle(@RequestBody Shuttle shuttle) {
        Shuttle savedShuttle = shuttleService.save(shuttle);
        return new ResponseEntity<>(savedShuttle, HttpStatus.CREATED);
    }

    // Endpoint: /api/shuttles (GET) - View all shuttles
    @GetMapping
    public List<Shuttle> getAllShuttles() {
        return shuttleService.findAll();
    }
}