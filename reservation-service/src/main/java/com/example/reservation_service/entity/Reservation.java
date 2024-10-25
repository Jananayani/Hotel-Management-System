package com.example.reservation_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    private int userId;
    private int roomNumber;
    private String checkedInDate;
    private String checkedOutDate;
//    private String status;

    @ElementCollection
    private List<Integer> roomIds;
}
