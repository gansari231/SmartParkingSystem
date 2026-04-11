package com.parking.repository;

import com.parking.entity.ParkingSpot;
import com.parking.enums.SpotType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    List<ParkingSpot> findBySpotTypeAndIsAvailableTrue(SpotType spotType);
}