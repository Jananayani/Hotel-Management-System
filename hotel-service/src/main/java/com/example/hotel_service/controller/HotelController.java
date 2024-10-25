package com.example.hotel_service.controller;

import com.example.hotel_service.dto.RoomDto;
import com.example.hotel_service.entity.Hotel;
import com.example.hotel_service.entity.Room;
import com.example.hotel_service.entity.RoomType;
import com.example.hotel_service.repository.HotelRepository;
import com.example.hotel_service.repository.RoomRepository;
import com.example.hotel_service.repository.RoomTypeRepository;
import com.example.hotel_service.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/hotel")
@RestController
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Hotel createHotel(@Valid @RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }

//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable Integer id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.ok("Hotel deleted");
    }


    @PostMapping("/rooms/create")
    public ResponseEntity<String> createRoom(@Valid @RequestBody RoomDto roomDto, BindingResult result) {
        if(result.hasErrors()){
            String errorMessage = result.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        List<Integer> roomNumber = roomRepository.findByRoomNumber(roomDto.getRoomNumber());
        System.out.println("roomNumber:   "+roomNumber);
        if(!(roomNumber.isEmpty())){
            return ResponseEntity.ok("Room number already created");

        }
        Optional<RoomType> roomType = roomTypeRepository.findById(roomDto.getRoomTypeId());
        System.out.println("roomType:   "+roomType);
        if(roomType.equals(Optional.empty())){
            return ResponseEntity.ok("Room Type not found");

        }

        Optional<Hotel> hotelId = hotelRepository.findById(roomDto.getHotelId());
        System.out.println("hotelId:    "+hotelId);
        if(hotelId.isEmpty()){
            return ResponseEntity.ok("Hotel Id not found");

        }
        hotelService.createRoom(roomDto);
        return ResponseEntity.ok("Room created");
    }

    @GetMapping("/rooms")
    public List<Room> getAllRooms(){
        return hotelService.getRooms();
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable int id){
        hotelService.deleteRoom(id);
        return ResponseEntity.ok("Room deleterd");

    }

    @GetMapping("/availableRooms")
    public ResponseEntity<List<Integer>> getAvailableRooms
            (){
        return hotelService.getAvailableRooms();
    }
}

