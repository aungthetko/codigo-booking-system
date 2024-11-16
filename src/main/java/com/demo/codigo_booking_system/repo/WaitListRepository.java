package com.demo.codigo_booking_system.repo;

import com.demo.codigo_booking_system.modal.waitlist.Waitlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitListRepository extends JpaRepository<Waitlist, Long> {

}
