package com.demo.codigo_booking_system.controller;

import com.demo.codigo_booking_system.modal.user.User;
import com.demo.codigo_booking_system.modal.user.UserPrincipal;
import com.demo.codigo_booking_system.service.user.UserService;
import com.demo.codigo_booking_system.utility.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJWTHeader(userPrincipal);
        return ResponseEntity.ok().headers(jwtHeader).body(loginUser);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user){
        User newUser = userService.registerUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getProfileInfo(@PathVariable("id") Long id){
        User user = userService.getProfileInfo(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/resetpassword")
    public ResponseEntity<JSONObject> resetPassword(@RequestParam("email") String email){
        JSONObject response = userService.resetPassword(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/changepassword")
    public String changePassword(@RequestParam("email") String email, @RequestParam("password") String password){
        return userService.changePassword(email, password);
    }

    private void authenticate(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJWTHeader(UserPrincipal userPrincipal) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Jwt-Token", jwtTokenProvider.generateToken(userPrincipal));
        return httpHeaders;
    }

}
