package com.example.mywebapp.controller;

import com.example.mywebapp.model.VehicleLocation;
import com.example.mywebapp.service.VehicleLocationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class LocationController {

    @Autowired
    private VehicleLocationService locationService;

    @GetMapping("/api/locations")
    public List<VehicleLocation> getAllLocations() {
        return locationService.getAllLocations();
    }
   
    
}
