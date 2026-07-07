package com.campustracker.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "trips")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    @Column(name = "current_status")
    private String currentStatus;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "trip_date")
    private LocalDate tripDate;

    @Column(name = "shuttle_id")
    private Long shuttleId;

    // ✅ Relationship with TripLog
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  // ✅ Prevents infinite recursion when fetching logs
    private List<TripLog> tripLogs = new ArrayList<>();

    // ✅ Getters & Setters
    public Long getTripId() {
        return tripId;
    }
    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }
    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }

    public Long getShuttleId() {
        return shuttleId;
    }
    public void setShuttleId(Long shuttleId) {
        this.shuttleId = shuttleId;
    }

    public List<TripLog> getTripLogs() {
        return tripLogs;
    }
    public void setTripLogs(List<TripLog> tripLogs) {
        this.tripLogs = tripLogs;
    }
}
