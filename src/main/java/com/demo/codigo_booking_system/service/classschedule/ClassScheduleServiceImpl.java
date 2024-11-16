package com.demo.codigo_booking_system.service.classschedule;

import com.demo.codigo_booking_system.modal.classes.ClassSchedule;
import com.demo.codigo_booking_system.repo.ClassScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClassScheduleServiceImpl implements ClassScheduleService {

    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    @Override
    public ClassSchedule saveClass(ClassSchedule classSchedule) {
        return classScheduleRepository.save(classSchedule);
    }

    @Override
    public List<ClassSchedule> getAllClassSchedule() {
        return classScheduleRepository.findAll();
    }

    @Override
    public ClassSchedule findClassById(Long id) {
        return classScheduleRepository.findById(id).stream()
                .findFirst().orElseThrow(() -> new IllegalStateException("Class not found!"));
    }

    @Override
    public List<ClassSchedule> findClassesForRefunds() {
        return classScheduleRepository.findByEndTimeBefore(LocalDateTime.now());
    }

}
