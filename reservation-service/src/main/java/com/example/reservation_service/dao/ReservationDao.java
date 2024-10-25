package com.example.reservation_service.dao;

import com.example.reservation_service.dto.ReservationDto;
import com.example.reservation_service.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationDao extends JpaRepository<Reservation,Integer> {
    @Query(value = "SELECT * FROM reservation r Where r.id=:reservationId", nativeQuery = true)
    String findByReservationId(Integer reservationId);
}
