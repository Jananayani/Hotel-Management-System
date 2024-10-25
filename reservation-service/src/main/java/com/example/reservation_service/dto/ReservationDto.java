package com.example.reservation_service.dto;

import lombok.Data;

@Data

public class ReservationDto {
//    int id;
    int roomNumber;
//    int userId;
//    String status;
    String checkedInDate;
    String checkedOutDate;
}
