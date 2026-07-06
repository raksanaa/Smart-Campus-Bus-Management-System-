package com.project.eveningshuttle.service;

import org.springframework.stereotype.Service;

@Service
public class ShuttleLocationService {
    public int ETA;

    public void calculateETA(double longitude, double latitude) {
        ETA = 10;
    }

}