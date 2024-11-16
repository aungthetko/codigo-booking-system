package com.demo.codigo_booking_system.controller;

import com.demo.codigo_booking_system.modal.classes.ClassSchedule;
import com.demo.codigo_booking_system.service.classschedule.ClassScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/classschedule")
@RequiredArgsConstructor
public class ClassScheduleController {

    private final ClassScheduleService classScheduleService;

    @PostMapping("/save")
    public ResponseEntity<ClassSchedule> saveClassSchedule(@RequestBody ClassSchedule classSchedule){
        ClassSchedule newClassSchedule = classScheduleService.saveClass(classSchedule);
        return new ResponseEntity<>(newClassSchedule, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClassSchedule>> getAllClassSchedule(){
        return new ResponseEntity<>(classScheduleService.getAllClassSchedule(), HttpStatus.OK);
    }

}
