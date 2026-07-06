package com.project.eveningshuttle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.eveningshuttle.service.AddPassengerService;

@RestController
@CrossOrigin
public class AddPassengerController {

    private final AddPassengerService addPassengerService;

    @Autowired
    public AddPassengerController(AddPassengerService addPassengerService) {
        this.addPassengerService = addPassengerService;
    }

    @PostMapping("/addPassenger")
    public String addPassenger(@RequestParam int suid, @RequestParam int address) {
        return addPassengerService.addPassenger(suid, address);
    }
}
