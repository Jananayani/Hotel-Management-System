package com.example.hotel_service.dao;

import com.example.hotel_service.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomDao extends JpaRepository<Room, Integer> {
    @Query(value = "SELECT r.room_number FROM room r Where r.is_active=true", nativeQuery = true)
    List<Integer> findAvailableRooms();

}
