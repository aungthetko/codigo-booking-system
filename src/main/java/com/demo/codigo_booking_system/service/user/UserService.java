package com.demo.codigo_booking_system.service.user;

import com.demo.codigo_booking_system.modal.user.User;
import org.json.JSONObject;

public interface UserService {

    User registerUser(User user);

    User getProfileInfo(Long id);

    JSONObject resetPassword(String email);

    String changePassword(String email, String password);

    User findUserByUsername(String username);
}
