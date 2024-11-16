package com.demo.codigo_booking_system.service.waitlist;

import com.demo.codigo_booking_system.modal.waitlist.Waitlist;
import com.demo.codigo_booking_system.repo.WaitListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaitListServiceImpl implements WaitListService {

    @Autowired
    private WaitListRepository waitListRepository;

    @Override
    public Waitlist saveWaitList(Waitlist waitList) {
        return waitListRepository.save(waitList);
    }

    @Override
    public void delete(Waitlist waitlist) {
        waitListRepository.delete(waitlist);
    }
}
