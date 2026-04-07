package com.parking.entity;

import com.parking.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Vehicle vehicle;

    @ManyToOne
    private ParkingSpot spot;

    private LocalDateTime entryTime;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}