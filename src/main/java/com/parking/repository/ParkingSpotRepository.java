package com.parking.repository;

import com.parking.entity.ParkingSpot;
import com.parking.enums.SpotType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    //List<ParkingSpot> findBySpotTypeAndIsAvailableTrue(SpotType spotType);

    @Query("SELECT s FROM ParkingSpot s WHERE s.spotType = :spotType AND s.isAvailable = true ORDER BY s.id ASC")
    @Lock(LockModeType.PESSIMISTIC_WRITE)   // database-level row lock
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000"))
    List<ParkingSpot> findBySpotTypeAndIsAvailableTrue(@Param("spotType") SpotType spotType);

    long countByisAvailableTrue();

    long countByisAvailableFalse();

    long count();
}