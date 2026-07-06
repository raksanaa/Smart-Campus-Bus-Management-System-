package com.project.eveningshuttle.factory;

import com.project.eveningshuttle.model.runtime.Passenger;
import com.project.eveningshuttle.model.runtime.StudentPassenger;

public class PassengerFactory {

    public static Passenger createPassenger(String passengerType, int suid, int address) {
        if (passengerType == null) {
            throw new IllegalArgumentException("Passenger type cannot be null");
        }

        switch (passengerType.toUpperCase()) {
            case "STUDENT":
                return new StudentPassenger(suid, address);
            // We can add more types here like "GUEST", "VIP", etc.
            default:
                throw new IllegalArgumentException("Unsupported passenger type: " + passengerType);
        }
    }
}
