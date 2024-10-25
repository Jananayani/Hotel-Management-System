package com.example.reservation_service.service;

import com.example.reservation_service.dao.ReservationDao;
import com.example.reservation_service.dto.ReservationDto;
import com.example.reservation_service.entity.Reservation;
import com.example.reservation_service.feign.ReservationInterface;
import com.example.reservation_service.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationInterface reservationInterface;

    @Autowired
    private ReservationDao reservationDao;

    public ResponseEntity<String> createReservation(Integer roomNumber, String checkedInDate, String checkedOutDate) {
//        String status = null;
        List<Integer> reservations = reservationInterface.getAvailableRooms().getBody();

        if (reservations != null && reservations.contains(roomNumber)) {
            Reservation reservation = new Reservation();
            System.out.println("reser:  "+reservation);
            reservation.setCheckedInDate(checkedInDate);
            reservation.setCheckedOutDate(checkedOutDate);
            reservation.setRoomNumber(roomNumber);
//            reservation.setUserId(userId);
            reservationDao.save(reservation);

            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Room number is not available.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateReservation(Integer id, ReservationDto updatedReservation) {
        System.out.println("updatedReservation:     "+ updatedReservation);
        try {
            Optional<Reservation> existingReservation = reservationDao.findById(id);
//            String status=null;
            List<Integer> reservations = reservationInterface.getAvailableRooms().getBody();
            System.out.println("reservations:    "+reservations);
//            if (reservations != null && reservations.contains(roomNumber)) {
//
//            }
            if (!existingReservation.isPresent()) {
                return new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
            }

            Reservation reservation = existingReservation.get();
            reservation.setCheckedInDate(updatedReservation.getCheckedInDate());
            reservation.setCheckedOutDate(updatedReservation.getCheckedOutDate());
            reservation.setRoomNumber(updatedReservation.getRoomNumber());

            System.out.println( "reservation:     "+ reservation);
            reservationDao.save(reservation);
            return new ResponseEntity<>("Reservation updated successfully", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error updating reservation", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }

//    public ResponseEntity<String> createRoomNumberList(String status, String checkedInDate, String checkedOutDate) {
//
//        List<Integer> rooms = reservationInterface.getAvailableRooms(status).getBody();
//        Reservation reservation = new Reservation();
//        reservation.setCheckedInDate(checkedInDate);
//        reservation.setCheckedOutDate(checkedOutDate);
//        reservation.setRoomIds(rooms);
//        reservation.setStatus(status);
//        reservationDao.save(reservation);
//
//        return new ResponseEntity<>("Success", HttpStatus.CREATED);
//
//    }
}
