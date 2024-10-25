package com.example.hotel_service.repository;

import com.example.hotel_service.entity.Room;
import com.example.hotel_service.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

}
