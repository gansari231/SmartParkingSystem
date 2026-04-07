package com.parking.service;

import com.parking.entity.*;
import com.parking.enums.SpotType;
import com.parking.enums.TicketStatus;
import com.parking.enums.VehicleType;
import com.parking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingSpotRepository spotRepository;
    private final VehicleRepository vehicleRepository;
    private final TicketRepository ticketRepository;

    public Ticket parkVehicle(Vehicle vehicle) {

        // 1. Save vehicle
        vehicleRepository.save(vehicle);

        // 2. Find available spot
        ParkingSpot spot = (ParkingSpot) spotRepository
                .findBySpotTypeAndIsAvailableTrue(
                        SpotType.valueOf(vehicle.getType().name())
                )
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No spot available"));

        // 3. Mark spot occupied
        spot.setAvailable(false);
        spotRepository.save(spot);

        // 4. Create ticket
        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setSpot(spot);
        ticket.setEntryTime(LocalDateTime.now());
        ticket.setStatus(TicketStatus.ACTIVE);

        return ticketRepository.save(ticket);
    }
}