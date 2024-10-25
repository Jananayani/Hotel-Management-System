package com.example.hotel_service.repository;

import com.example.hotel_service.dto.RoomDto;
import com.example.hotel_service.entity.Hotel;
import com.example.hotel_service.entity.Room;
import com.example.hotel_service.entity.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {


}
