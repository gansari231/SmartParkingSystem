package com.parking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParkingStatsDTO {

    private long totalSpots;
    private long availableSpots;
    private long occupiedSpots;
}