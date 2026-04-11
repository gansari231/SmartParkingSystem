package com.parking.dto;

import com.parking.enums.VehicleType;
import lombok.Data;

@Data
public class EntryRequestDTO {

    private String vehicleNumber;
    private VehicleType type;
}