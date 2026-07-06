package com.project.eveningshuttle.state;

import com.project.eveningshuttle.model.runtime.Passenger;
import com.project.eveningshuttle.service.ShuttleService;

public class InTransitState implements ShuttleState {

    @Override
    public String handleAddPassenger(ShuttleService shuttleService, Passenger passenger) {
        return "Cannot board. Shuttle is currently in transit.";
    }

    @Override
    public String handleGetETA(ShuttleService shuttleService) {
        return "ETA " + shuttleService.getShuttleLocationService().ETA + " seconds. Shuttle is in transit.";
    }

    @Override
    public void handleStartJourney(ShuttleService shuttleService) {
        // Already in transit
    }
}
