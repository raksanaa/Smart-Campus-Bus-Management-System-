package com.project.eveningshuttle.controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.eveningshuttle.service.GetShuttleStatusService;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin
public class GetShuttleStatusController {

    private final GetShuttleStatusService getShuttleStatusService;

    @Autowired
    public GetShuttleStatusController(GetShuttleStatusService getShuttleStatusService) {
        this.getShuttleStatusService = getShuttleStatusService;
    }

    @GetMapping("/requestShuttleStatus")
    public String getStatus() {
        return getShuttleStatusService.getStatus();
    }
}
