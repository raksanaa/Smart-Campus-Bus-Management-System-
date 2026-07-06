package com.project.eveningshuttle.controller;

import org.springframework.web.bind.annotation.*;

import com.project.eveningshuttle.service.RequestPickupService;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@CrossOrigin
public class RequestPickupController {

    private final RequestPickupService pickupService;

    // ResponseEntity

    @Autowired
    public RequestPickupController(RequestPickupService pickupService) {
        this.pickupService = pickupService;
    }

    @GetMapping("/requestPickup")
    public String requestPickup(@RequestParam int suid) {
        return pickupService.isValid(suid);
    }
}
