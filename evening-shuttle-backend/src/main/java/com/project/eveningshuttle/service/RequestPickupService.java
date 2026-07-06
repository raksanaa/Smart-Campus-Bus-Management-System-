package com.project.eveningshuttle.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.eveningshuttle.factory.PassengerFactory;
import com.project.eveningshuttle.model.entity.StudentEntity;
import com.project.eveningshuttle.repository.StudentRepository;

@Service
public class RequestPickupService {

    private final StudentRepository studentRepository;
    private final ShuttleService shuttleService;
    private final GetShuttleStatusService shuttleStatusService;

    @Autowired
    public RequestPickupService(StudentRepository studentRepository, ShuttleService shuttleService,
            GetShuttleStatusService shuttleStatusService) {
        this.studentRepository = studentRepository;
        this.shuttleService = shuttleService;
        this.shuttleStatusService = shuttleStatusService;
    }

    public synchronized String isValid(int suid) {
        Optional<StudentEntity> student = studentRepository.findById(suid);
        if (student.isPresent()) {
            String eta = shuttleStatusService.getStatus();
            String queueResponse;

            if (shuttleService.containsSuid(suid)) {
                queueResponse = "Passenger with SUID: " + suid + " has already booked.";
            } else {
                if (shuttleService.isInTransit()) {
                    queueResponse = "Cannot board. Shuttle is currently in transit.";
                } else {
                    queueResponse = shuttleService
                            .enqueuePendingPassenger(PassengerFactory.createPassenger("Student", suid, -1));
                }
            }

            return "Passenger Status: " + queueResponse + " Shuttle: " + eta;

        } else {
            return "Invalid SUID";
        }
    }

}
