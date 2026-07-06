package com.project.eveningshuttle.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.eveningshuttle.factory.PassengerFactory;
import com.project.eveningshuttle.model.entity.StudentEntity;
import com.project.eveningshuttle.model.runtime.Passenger;
import com.project.eveningshuttle.repository.StudentRepository;

@Service
public class AddPassengerService {

    private final StudentRepository studentRepository;
    private final ShuttleService shuttleService;

    @Autowired
    public AddPassengerService(StudentRepository studentRepository, ShuttleService shuttleService) {
        this.studentRepository = studentRepository;
        this.shuttleService = shuttleService;
    }

    public synchronized String addPassenger(int suid, int address) {
        Optional<StudentEntity> student = studentRepository.findById(suid);
        if (student.isEmpty()) {
            return "Invalid SUID";
        }

        Passenger passenger = PassengerFactory.createPassenger("Student", suid, address);
        String success = shuttleService.addPassenger(passenger);

        return success;
    }
}

// Database connection and shuttle connection - singleton

// If no one has boarded yet, the bus waits indefinitely at the campus stop

// If even one student boards the bus, the bus waits for 30 seconds for other
// students

// Student req for a ride, server validates suid, if valid the server asks the
// user to board the bus within
// the:
// next 15 seconds if the no one has boarded the bus yet
// OR
// next x remaining seconds the bus is waiting

// if the user clicks on board within the remaining seconds, the user gets added
// to the queue. i.e. boarded
// if not, the user is taken back to the request ride phase

// Once the bus waiting timer hits 0, the bus starts its journey. Suppose it
// takes 10 secs to complete its journey.
// We will consider every journey takes 10 seconds.

// So, once the journey started, any new passenger requests should be added in
// the temporary queue, and those
// passengers should be shown an ETA.
// once, the journey gets over, that temporary queue should inject its values in
// the main queue and again
// the main queue should be open for new passengers.

// If there are passengers in the main queue, again the bus will wait for 30
// seconds and the process continues.