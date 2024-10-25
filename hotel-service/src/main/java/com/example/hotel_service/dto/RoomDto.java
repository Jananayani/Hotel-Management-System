package com.example.hotel_service.dto;

import com.example.hotel_service.entity.Hotel;
import com.example.hotel_service.entity.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Room number cannot be null")
    private int roomNumber;

    @NotBlank(message = "Room status is required")
    private String roomStatus;

    private Boolean isActive;

    @NotNull(message = "Room type id cannot be null")
    private Long roomTypeId;

    @NotNull(message = "hotel id cannot be null")
    private Long hotelId;
}
