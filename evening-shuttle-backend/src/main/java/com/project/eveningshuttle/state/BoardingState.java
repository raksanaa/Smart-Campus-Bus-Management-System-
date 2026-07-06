package com.project.eveningshuttle.state;

import com.project.eveningshuttle.model.runtime.Passenger;
import com.project.eveningshuttle.service.ShuttleService;

public class BoardingState implements ShuttleState {

    @Override
    public String handleAddPassenger(ShuttleService shuttleService, Passenger passenger) {
        return shuttleService.addPassengerInternal(passenger);
    }

    @Override
    public String handleGetETA(ShuttleService shuttleService) {
        long remaining = shuttleService.getWaitRemaining();
        return "ETA 0 seconds. Board at the Campus Stop within the next " +
                Math.max(remaining / 1000, 0) + " seconds.";
    }

    @Override
    public void handleStartJourney(ShuttleService shuttleService) {
        shuttleService.moveToInTransitState();
        shuttleService.beginShuttleJourney();
    }
}
