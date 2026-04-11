package com.parking.dto;

import com.parking.enums.TicketStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TicketResponseDTO {

    private Long ticketId;
    private String vehicleNumber;
    private String spotNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;
    private TicketStatus status;
}