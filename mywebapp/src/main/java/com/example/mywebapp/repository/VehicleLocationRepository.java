package com.example.mywebapp.repository;

import com.example.mywebapp.model.VehicleLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleLocationRepository extends JpaRepository<VehicleLocation, Long> {
    VehicleLocation findByUserId(String userId);
}
