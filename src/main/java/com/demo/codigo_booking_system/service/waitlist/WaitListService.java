package com.demo.codigo_booking_system.service.waitlist;

import com.demo.codigo_booking_system.modal.waitlist.Waitlist;

public interface WaitListService {

    Waitlist saveWaitList(Waitlist waitList);

    void  delete(Waitlist waitlist);
}
