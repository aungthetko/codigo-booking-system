package com.demo.codigo_booking_system.repo;

import com.demo.codigo_booking_system.modal.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
