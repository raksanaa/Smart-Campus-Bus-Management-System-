package com.project.eveningshuttle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetShuttleStatusService {

    private final ShuttleService shuttleService;

    @Autowired
    public GetShuttleStatusService(ShuttleService shuttleService) {
        this.shuttleService = shuttleService;
    }

    public String getStatus() {
        return shuttleService.getETA();
    }
}
