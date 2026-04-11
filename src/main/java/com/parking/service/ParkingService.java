package com.parking.service;

import com.parking.dto.EntryRequestDTO;
import com.parking.dto.TicketResponseDTO;
import com.parking.entity.*;
import com.parking.enums.SpotType;
import com.parking.entity.Vehicle;
import com.parking.enums.TicketStatus;
import com.parking.exception.ParkingException;
import com.parking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingSpotRepository spotRepository;
    private final VehicleRepository vehicleRepository;
    private final TicketRepository ticketRepository;

    /*public Ticket parkVehicle(EntryRequestDTO vehicle) {

        // 1. Save vehicle
        vehicleRepository.save(vehicle);

        // 2. Find available spot
        ParkingSpot spot = (ParkingSpot) spotRepository
                .findBySpotTypeAndIsAvailableTrue(
                        SpotType.valueOf(vehicle.getType().name())
                )
                .stream()
                .findFirst()
                .orElseThrow(()-> new ParkingException("No parking spot available for " + vehicle.getType()));

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
    }*/

    @Transactional
    public TicketResponseDTO parkVehicle(EntryRequestDTO request) {

        // 1. Create vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(request.getVehicleNumber());
        vehicle.setType(request.getType());

        vehicleRepository.save(vehicle);

        // 2. Allocate spot
        ParkingSpot spot = spotRepository
                .findBySpotTypeAndIsAvailableTrue(SpotType.valueOf(request.getType().name()))
                .stream()
                .findFirst()
                .orElseThrow(() -> new ParkingException("No parking spot available"));

        spot.setAvailable(false);
        spotRepository.save(spot);

        // 3. Create ticket
        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setSpot(spot);
        ticket.setEntryTime(LocalDateTime.now());
        ticket.setStatus(TicketStatus.ACTIVE);

        ticketRepository.save(ticket);

        // 4. Convert to DTO
        return TicketResponseDTO.builder()
                .ticketId(ticket.getId())
                .vehicleNumber(vehicle.getVehicleNumber())
                .spotNumber(spot.getSpotNumber())
                .entryTime(ticket.getEntryTime())
                .status(ticket.getStatus())
                .build();
    }

    /*public Ticket exitVehicle(Long ticketId) {

        // 1. Fetch ticket
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ParkingException("Ticket not found"));

        // 2. Set exit time
        ticket.setExitTime(LocalDateTime.now());

        // 3. Calculate duration (in hours)
        long hours = Math.max(1,
                Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toHours()
        );

        // 4. Calculate fee
        double fee = switch (ticket.getVehicle().getType()) {
            case BIKE -> hours * 10;
            case CAR -> hours * 20;
            case BUS -> hours * 50;
        };

        ticket.setFee(fee);
        ticket.setStatus(TicketStatus.COMPLETED);

        // 5. Free parking spot
        ParkingSpot spot = ticket.getSpot();
        spot.setAvailable(true);
        spotRepository.save(spot);

        // 6. Save ticket
        return ticketRepository.save(ticket);
    }*/

    public TicketResponseDTO exitVehicle(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ParkingException("Ticket not found"));

        ticket.setExitTime(LocalDateTime.now());

        long hours = Math.max(1,
                Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toHours()
        );

        double fee = switch (ticket.getVehicle().getType()) {
            case BIKE -> hours * 10;
            case CAR -> hours * 20;
            case BUS -> hours * 50;
        };

        ticket.setFee(fee);
        ticket.setStatus(TicketStatus.COMPLETED);

        ParkingSpot spot = ticket.getSpot();
        spot.setAvailable(true);
        spotRepository.save(spot);

        ticketRepository.save(ticket);

        return TicketResponseDTO.builder()
                .ticketId(ticket.getId())
                .vehicleNumber(ticket.getVehicle().getVehicleNumber())
                .spotNumber(ticket.getSpot().getSpotNumber())
                .entryTime(ticket.getEntryTime())
                .exitTime(ticket.getExitTime())
                .fee(ticket.getFee())
                .status(ticket.getStatus())
                .build();
    }
}