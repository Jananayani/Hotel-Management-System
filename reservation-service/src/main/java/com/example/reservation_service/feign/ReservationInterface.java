package com.example.reservation_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("HOTEL-SERVICE")
public interface ReservationInterface {
    @GetMapping("/api/v1/hotel/availableRooms")
    public ResponseEntity<List<Integer>> getAvailableRooms
            ();
}
