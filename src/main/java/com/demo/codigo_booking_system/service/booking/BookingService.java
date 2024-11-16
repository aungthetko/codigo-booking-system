package com.demo.codigo_booking_system.service.booking;


public interface BookingService {

    String doBooking(String email, Long classId);

    String cancelBooking(Long userId, Long classId);

}
