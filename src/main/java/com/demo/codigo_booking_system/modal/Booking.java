package com.demo.codigo_booking_system.modal;

import com.demo.codigo_booking_system.modal.classes.ClassSchedule;
import com.demo.codigo_booking_system.modal.user.User;
import com.demo.codigo_booking_system.modal.userpackage.Packages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "class_schedule_id")
    private ClassSchedule bookedClass;

    private LocalDateTime bookingTime;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Packages userPackage; // Tracks the package used for the booking

}
