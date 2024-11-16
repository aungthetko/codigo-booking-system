package com.demo.codigo_booking_system.service.refund;

import com.demo.codigo_booking_system.modal.classes.ClassSchedule;
import com.demo.codigo_booking_system.modal.user.User;
import com.demo.codigo_booking_system.modal.userpackage.Packages;
import com.demo.codigo_booking_system.modal.waitlist.Waitlist;
import com.demo.codigo_booking_system.repo.PackageRepository;
import com.demo.codigo_booking_system.repo.UserRepository;
import com.demo.codigo_booking_system.service.classschedule.ClassScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassRefundScheduler {

    private final ClassScheduleService classScheduleService;
    private final UserRepository userRepository;
    private final PackageRepository packageRepository;

    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void processClassRefunds(){
        List<ClassSchedule> classesToProcess = classScheduleService.findClassesForRefunds();
        for (ClassSchedule classSchedule : classesToProcess) {
            for (Waitlist waitlist : classSchedule.getWaitlists()) {
                User user = waitlist.getUser();

                Optional<Packages> userPackage = user.getPackages().stream()
                        .filter(pkg -> pkg.getCountry().equalsIgnoreCase(classSchedule.getCountry()))
                        .findFirst();

                if (userPackage.isPresent()) {
                    Packages pkg = userPackage.get();
                    pkg.setCredits(pkg.getCredits() + classSchedule.getRequiredCredits());
                    packageRepository.save(pkg);  // Save the updated package back to the DB
                }
            }
            classScheduleService.saveClass(classSchedule);
        }
    }
}
