package com.example.hotel_service.repository;

import com.example.hotel_service.dto.RoomDto;
import com.example.hotel_service.entity.Hotel;
import com.example.hotel_service.entity.Room;
import com.example.hotel_service.entity.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query(value = "SELECT r.room_number FROM room r Where r.room_number=:roomNumber", nativeQuery = true)
    List<Integer> findByRoomNumber(Integer roomNumber);

    @Query(value = "SELECT h.hotel_id FROM hotel h Where h.hotel_id=:hotelId", nativeQuery = true)
    List<Integer> findByHotelId(Integer hotelId);

}
