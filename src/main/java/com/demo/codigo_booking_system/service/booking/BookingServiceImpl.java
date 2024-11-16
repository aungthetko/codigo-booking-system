package com.demo.codigo_booking_system.service.booking;

import com.demo.codigo_booking_system.exception.custom.NotEligibleException;
import com.demo.codigo_booking_system.exception.custom.UserNotFoundException;
import com.demo.codigo_booking_system.modal.Booking;
import com.demo.codigo_booking_system.modal.classes.ClassSchedule;
import com.demo.codigo_booking_system.modal.user.User;
import com.demo.codigo_booking_system.modal.userpackage.Packages;
import com.demo.codigo_booking_system.modal.waitlist.Waitlist;
import com.demo.codigo_booking_system.repo.BookingRepository;
import com.demo.codigo_booking_system.repo.PackageRepository;
import com.demo.codigo_booking_system.repo.UserRepository;
import com.demo.codigo_booking_system.service.classschedule.ClassScheduleService;
import com.demo.codigo_booking_system.service.waitlist.WaitListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

//    private final RedisTemplate<String, Integer> redisTemplate;

    private final UserRepository userRepository;

    private final ClassScheduleService classScheduleService;

    private final BookingRepository bookingRepository;

    private final PackageRepository packageRepository;

    private final WaitListService waitListService;


    @Override
    public String doBooking(String email, Long classId) {
        User user = userRepository.findUserByEmail(email)
                .stream().findFirst()
                .orElseThrow(() -> new UserNotFoundException("User was not found"));

        ClassSchedule classSchedule = classScheduleService.findClassById(classId);

        Optional<Packages> userPackage = Optional.empty();
        for (Packages pkg : user.getPackages()) {
            if (pkg.getCountry().equalsIgnoreCase(classSchedule.getCountry()) &&
                    pkg.getCredits() >= classSchedule.getRequiredCredits()) {
                userPackage = Optional.of(pkg);
                break;
            }
        }

        if(userPackage.isEmpty()){
            throw new RuntimeException("Insufficient credits or no valid package available.");
        }else if(!user.getCountry().equals(classSchedule.getCountry())){
            throw new NotEligibleException("Not eligible to book this class | Country missmatch");
        }

        if (classSchedule.getAvailableSlots() > 0) {
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setBookedClass(classSchedule);
            booking.setBookingTime(LocalDateTime.now());
            bookingRepository.save(booking);

            userPackage.get().setCredits(userPackage.get().getCredits() - classSchedule.getRequiredCredits());
            packageRepository.save(userPackage.get());

            classSchedule.setAvailableSlots(classSchedule.getAvailableSlots() - 1);
            classSchedule.getBookings().add(booking);
            classScheduleService.saveClass(classSchedule);

            return "Booking Successfully";
        }else {
            Waitlist waitlist = new Waitlist();
            waitlist.setUser(user);
            waitlist.setWaitlistClass(classSchedule);
            waitlist.setWaitlistTime(LocalDateTime.now());
            waitListService.saveWaitList(waitlist);

            classScheduleService.saveClass(classSchedule);
            return "No available slots, added to waitlist.";
        }
    }

    @Override
    public String cancelBooking(Long userId, Long classId) {
        ClassSchedule classSchedule = classScheduleService.findClassById(classId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Booking bookingToCancel = null;
        for (Booking booking : classSchedule.getBookings()) {
            if (booking.getUser().getId() == userId) {
                bookingToCancel = booking;
                break;
            }
        }

        if (bookingToCancel == null) {
            throw new IllegalStateException("User is not booked for this class");
        }

        LocalDateTime classStartTime = classSchedule.getStartTime();
        LocalDateTime currentTime = LocalDateTime.now();
        long hoursDifference = Duration.between(currentTime, classStartTime).toHours();

        // Refund
        if (hoursDifference >= 4) {
            Packages userPackage = bookingToCancel.getUserPackage();
            if (userPackage != null) {
                userPackage.setCredits(userPackage.getCredits() + classSchedule.getRequiredCredits());
                packageRepository.save(userPackage);
            }else {
                // If cancelled within 4 hours, no refund, just return a message
                return "Cancellation within 4 hours of class start. No refund issued.";
            }
        }
        Packages userPackage = bookingToCancel.getUserPackage();
        if (userPackage != null) {
            userPackage.setCredits(userPackage.getCredits() + classSchedule.getRequiredCredits());
            packageRepository.save(userPackage);
        }
        classSchedule.getBookings().remove(bookingToCancel);
        bookingRepository.delete(bookingToCancel);

        if (!classSchedule.getWaitlists().isEmpty()) {
            Waitlist waitlistedUser = classSchedule.getWaitlists().remove(0);
            User newBookedUser = waitlistedUser.getUser();
            Optional<Packages> newUserPackage = newBookedUser.getPackages().stream()
                    .filter(pkg -> pkg.getCountry().equalsIgnoreCase(classSchedule.getCountry())
                            && pkg.getCredits() >= classSchedule.getRequiredCredits())
                    .findFirst();
            if (newUserPackage.isPresent()) {
                Packages pkg = newUserPackage.get();
                pkg.setCredits(pkg.getCredits() - classSchedule.getRequiredCredits());
                packageRepository.save(pkg);

                Booking newBooking = new Booking();
                newBooking.setUser(newBookedUser);
                newBooking.setBookedClass(classSchedule);
                newBooking.setUserPackage(pkg);
                newBooking.setBookingTime(LocalDateTime.now());
                bookingRepository.save(newBooking);
            }else{
                throw new RuntimeException("Waitlisted user has insufficient credits for booking");
            }
            waitListService.delete(waitlistedUser);
        }else{
            classSchedule.setAvailableSlots(classSchedule.getAvailableSlots() + 1);
        }
        classScheduleService.saveClass(classSchedule);
        return "Booking cancellation successful. Waitlisted user added to booking if available.";
    }
}
