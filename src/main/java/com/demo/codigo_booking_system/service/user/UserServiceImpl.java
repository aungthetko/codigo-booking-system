package com.demo.codigo_booking_system.service.user;

import com.demo.codigo_booking_system.exception.custom.UserNotFoundException;
import com.demo.codigo_booking_system.modal.user.User;
import com.demo.codigo_booking_system.modal.user.UserPrincipal;
import com.demo.codigo_booking_system.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.demo.codigo_booking_system.enumeration.Role.ROLE_ADMIN;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User was not found"));
        return new UserPrincipal(user);
    }

    @Override
    public User registerUser(User user) {
        String encodedPassword = encodePassword(user.getPassword());
        user.setPassword(encodedPassword);
        user.setAuthorities(ROLE_ADMIN.getAuthorities());
        user.setRole(ROLE_ADMIN.name());
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());
        return userRepository.save(user);
    }

    @Override
    public User getProfileInfo(Long id) {
        User user = userRepository.findById(id).stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User was not found"));
        return user;
    }

    @Override
    public JSONObject resetPassword(String email) {
        JSONObject response = new JSONObject();
        User user = userRepository.findUserByEmail(email).stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User was not found"));
        if(user != null){
            response.put("message", "Password Successfully Reset");
            response.put("temporaryPassword", RandomStringUtils.randomAlphanumeric(8));
        }
        return response;
    }

    @Override
    public String changePassword(String email, String password) {
        User user = userRepository.findUserByEmail(email)
                .stream().findFirst()
                .orElseThrow(() -> new UserNotFoundException("User was not found"));

        if(user != null){
            String encodedPassword = encodePassword(password);
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
        return "Password Successfully Changed!";
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User was not found"));
    }

    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
}
