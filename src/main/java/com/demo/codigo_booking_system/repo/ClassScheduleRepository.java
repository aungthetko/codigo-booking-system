package com.demo.codigo_booking_system.repo;

import com.demo.codigo_booking_system.modal.classes.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {

    List<ClassSchedule> findByEndTimeBefore(LocalDateTime endTime);

}
