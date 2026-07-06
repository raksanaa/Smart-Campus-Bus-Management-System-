package com.project.eveningshuttle.state;

import com.project.eveningshuttle.model.runtime.Passenger;
import com.project.eveningshuttle.service.ShuttleService;

public interface ShuttleState {
    String handleAddPassenger(ShuttleService shuttleService, Passenger passenger);

    String handleGetETA(ShuttleService shuttleService);

    void handleStartJourney(ShuttleService shuttleService);
}
