package com.example.mywebapp.service;

import com.example.mywebapp.model.VehicleLocation;
import com.example.mywebapp.repository.VehicleLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class VehicleLocationService {

    @Autowired
    private VehicleLocationRepository repository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public void saveOrUpdateLocation(VehicleLocation location) {
        VehicleLocation existingLocation = repository.findByUserId(location.getUserId());
        if (existingLocation != null) {
            existingLocation.setLatitude(location.getLatitude());
            existingLocation.setLongitude(location.getLongitude());
            existingLocation.setSpeed(location.getSpeed());
            existingLocation.setTimestamp(LocalDateTime.now().format(formatter));
            repository.save(existingLocation);
        } else {
            location.setTimestamp(LocalDateTime.now().format(formatter));
            repository.save(location);
        }
    }

    public void deleteLocationByUserId(String userId) {
        VehicleLocation existingLocation = repository.findByUserId(userId);
        if (existingLocation != null) {
            repository.delete(existingLocation);
        }
    }

    public List<VehicleLocation> getAllLocations() {
        return repository.findAll();
    }

    @Scheduled(fixedRate = 60000) // Runs every minute
    public void cleanUpStaleEntries() {
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);
        List<VehicleLocation> allLocations = repository.findAll();
        for (VehicleLocation location : allLocations) {
            LocalDateTime locationTime = LocalDateTime.parse(location.getTimestamp(), formatter);
            if (locationTime.isBefore(oneMinuteAgo)) {
                repository.delete(location);
            }
        }
    }
}
