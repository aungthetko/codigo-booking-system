package com.demo.codigo_booking_system.service.classschedule;

import com.demo.codigo_booking_system.modal.classes.ClassSchedule;

import java.util.List;

public interface ClassScheduleService {

    ClassSchedule saveClass(ClassSchedule classSchedule);

    List<ClassSchedule> getAllClassSchedule();

    ClassSchedule findClassById(Long id);

    List<ClassSchedule> findClassesForRefunds();

}
