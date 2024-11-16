package com.demo.codigo_booking_system.controller;

import com.demo.codigo_booking_system.service.booking.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<String> bookClass(@RequestParam("email") String email,
                                            @RequestParam("classId") Long classId){
            return new ResponseEntity<>(bookingService.doBooking(email, classId), HttpStatus.OK);
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelBooking(@RequestParam("userId") Long userId, @RequestParam("classId") Long classId){
        return new ResponseEntity<>(bookingService.cancelBooking(userId, classId), HttpStatus.OK);
    }
}
