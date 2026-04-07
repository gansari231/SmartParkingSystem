package com.parking.controller;

import com.parking.entity.ParkingSpot;
import com.parking.entity.Ticket;
import com.parking.entity.Vehicle;
import com.parking.repository.ParkingSpotRepository;
import com.parking.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingSpotRepository spotRepository;

    @Autowired
    private final ParkingService parkingService;

    @GetMapping("/spots")
    public List<ParkingSpot> getAllSpots() {
        return spotRepository.findAll();
    }

    @PostMapping("/entry")
    public Ticket parkVehicle(@RequestBody Vehicle vehicle) {
        return parkingService.parkVehicle(vehicle);
    }
}