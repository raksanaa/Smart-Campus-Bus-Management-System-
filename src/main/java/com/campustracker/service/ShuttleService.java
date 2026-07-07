package com.campustracker.service;

import com.campustracker.model.Shuttle;
import com.campustracker.repository.ShuttleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShuttleService {

    @Autowired
    private ShuttleRepository shuttleRepository;

    public Shuttle save(Shuttle shuttle) {
        // Here you could add logic like:
        // 1. Check if shuttleNumber already exists (though often handled by DB constraint)
        // 2. Normalize input data
        return shuttleRepository.save(shuttle);
    }

    public List<Shuttle> findAll() {
        return shuttleRepository.findAll();
    }
}