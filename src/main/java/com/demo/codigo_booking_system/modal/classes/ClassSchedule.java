package com.demo.codigo_booking_system.modal.classes;

import com.demo.codigo_booking_system.modal.Booking;
import com.demo.codigo_booking_system.modal.user.User;
import com.demo.codigo_booking_system.modal.waitlist.Waitlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int requiredCredits;
    private int availableSlots;

    @OneToMany(mappedBy = "bookedClass", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "waitlistClass", cascade = CascadeType.ALL)
    private List<Waitlist> waitlists = new ArrayList<>();


    public List<User> getBookedUsers() {
        List<User> bookedUsers = new ArrayList<>();
        for (Booking booking : bookings) {
            bookedUsers.add(booking.getUser());
        }
        return bookedUsers;
    }
}
