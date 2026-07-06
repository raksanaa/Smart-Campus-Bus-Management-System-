package com.project.eveningshuttle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EveningshuttleApplication {

	public static void main(String[] args) {
		SpringApplication.run(EveningshuttleApplication.class, args);
	}

}

// The shuttle makes one HTTP request to your server for every student who gets
// on the shuttle.
// (This is to tell the server that the student has entered the shuttle.
// This can be done when a student swipes the SUID card in the shuttle.)

// http://ip/addPassenger?suid=123456789&address="123 elm st, city, ST zip"
// Shuttle requests

// The shuttle makes one HTTP request to your server, every second, with its
// position (long,lat).

// http://ip/shuttleLocation?longitute=12.3&latitude=34.2 Shuttle requests

// http://ip/requestPickup?suid=123456789 Student requests

// Req pickup, Add to the bus

// Shuttle moves-> location updates -> ETA time changes
