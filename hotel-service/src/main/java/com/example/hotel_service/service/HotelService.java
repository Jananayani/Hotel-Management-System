package com.example.hotel_service.service;

import com.example.hotel_service.dao.RoomDao;
import com.example.hotel_service.dto.RoomDto;
import com.example.hotel_service.entity.Hotel;
import com.example.hotel_service.entity.Room;
import com.example.hotel_service.entity.RoomType;
import com.example.hotel_service.repository.HotelRepository;
import com.example.hotel_service.repository.RoomRepository;
import com.example.hotel_service.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    RoomDao roomDao;

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    public Room createRoom(RoomDto roomDto){

        System.out.println("roomDto: "+roomDto);
        Room room = new Room();

        room.setRoomNumber(roomDto.getRoomNumber());
        room.setRoomStatus(roomDto.getRoomStatus());

        // Fetch RoomType by roomTypeId from the repository
        Optional<RoomType> roomType = roomTypeRepository.findById(roomDto.getRoomTypeId());
        RoomType exsistingRoomType = roomType.get();
        // Fetch Hotel by hotelId from the repository
        Optional<Hotel> hotel = hotelRepository.findById(roomDto.getHotelId());
//
        Hotel existingHotel = hotel.get();
        // Set the roomType and hotel objects in the Room entity
        room.setRoomType(exsistingRoomType);
        room.setHotel(existingHotel);
        return roomRepository.save(room);
    }

    public List<Room> getRooms(){
        return roomRepository.findAll();
    }

    public void deleteRoom(int id){
        roomRepository.deleteById(id);
    }

    public ResponseEntity<List<Integer>> getAvailableRooms() {
        try {
            List<Integer> rooms = roomDao.findAvailableRooms();
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
