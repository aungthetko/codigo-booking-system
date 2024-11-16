package com.demo.codigo_booking_system.modal.waitlist;

import com.demo.codigo_booking_system.modal.classes.ClassSchedule;
import com.demo.codigo_booking_system.modal.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Waitlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_schedule_id", nullable = false)
    private ClassSchedule waitlistClass;

    private LocalDateTime waitlistTime;
}
