package com.parking.entity;

import com.parking.enums.SpotType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String spotNumber;

    @Enumerated(EnumType.STRING)
    private SpotType spotType;

    private boolean isAvailable = true;

    @Version
    private Integer version;
}