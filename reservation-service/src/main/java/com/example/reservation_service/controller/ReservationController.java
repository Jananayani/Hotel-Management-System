package com.example.reservation_service.controller;

import com.example.reservation_service.dao.ReservationDao;
import com.example.reservation_service.dto.ReservationDto;
import com.example.reservation_service.entity.Reservation;
import com.example.reservation_service.service.PdfService;
import com.example.reservation_service.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
//import java.net.http.HttpHeaders;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private ReservationDao reservationDao;

    @PostMapping("/create")
    public ResponseEntity<String> createReservation(@RequestBody ReservationDto reservationDto) {
        return reservationService.createReservation(reservationDto.getRoomNumber(),reservationDto.getCheckedInDate(),reservationDto.getCheckedOutDate());
    }

    @PutMapping("/update/{reservationId}")
    public ResponseEntity<String> updateReservation(@PathVariable Integer reservationId, @RequestBody ReservationDto reservationDto) {
        return reservationService.updateReservation(reservationId,reservationDto);
    }
    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable Integer id) {
        return reservationService.getReservationById(id);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<byte[]> generateReservationPdf(@PathVariable("id") Integer reservationId) throws IOException {
        // Mock reservation details for demonstration. You would actually retrieve this from the database.
        String reservation = reservationDao.findByReservationId(reservationId);
        String[] values = reservation.replace("reservation:", "").split(",");
        System.out.println("values:     "+values[4]);
        String reservationDetails = "Reservation Number: " + reservationId + "\n" +

                "Check-in: " + values[1] + "\n" +
                "Check-out: " + values[2] + "\n" +
                "Room Number: " + values[4];

        ByteArrayInputStream pdfStream = pdfService.generateReservationPdf(reservationDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reservation_" + reservationId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfStream.readAllBytes());
    }
}
