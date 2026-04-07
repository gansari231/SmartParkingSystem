package com.parking.repository;

import com.parking.entity.ParkingSpot;
import com.parking.enums.SpotType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    Collection<Object> findBySpotTypeAndIsAvailableTrue(SpotType spotType);
}