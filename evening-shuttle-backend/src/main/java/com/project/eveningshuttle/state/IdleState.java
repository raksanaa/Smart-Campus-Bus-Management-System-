package com.project.eveningshuttle.state;

import com.project.eveningshuttle.model.runtime.Passenger;
import com.project.eveningshuttle.service.ShuttleService;

public class IdleState implements ShuttleState {

    // @Override
    // public String handleAddPassenger(ShuttleService shuttleService, Passenger
    // passenger) {
    // String result = shuttleService.addPassengerInternal(passenger);
    // shuttleService.moveToBoardingState();
    // return result;

    // }

    @Override
    public String handleAddPassenger(ShuttleService shuttleService, Passenger passenger) {
        String result = shuttleService.addPassengerInternal(passenger);

        if (result.equals("Passenger Added")) {
            shuttleService.moveToBoardingState();
        }

        return result;
    }

    @Override
    public String handleGetETA(ShuttleService shuttleService) {
        return "ETA 0 seconds. Board at the Campus Stop";
    }

    @Override
    public void handleStartJourney(ShuttleService shuttleService) {
        // Do nothing
    }
}
