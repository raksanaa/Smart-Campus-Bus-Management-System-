package com.project.eveningshuttle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.eveningshuttle.service.ShuttleLocationService;

@RestController
@CrossOrigin
public class ShuttleLocationController {

    private final ShuttleLocationService shuttleLocationService;

    @Autowired
    public ShuttleLocationController(ShuttleLocationService shuttleLocationService) {
        this.shuttleLocationService = shuttleLocationService;
    }

    @PostMapping("/shuttleLocation")
    public String updateLocation(@RequestParam double longitude, @RequestParam double latitude) {
        shuttleLocationService.calculateETA(longitude, latitude);
        return "Location received and ETA updated.";
    }
}
