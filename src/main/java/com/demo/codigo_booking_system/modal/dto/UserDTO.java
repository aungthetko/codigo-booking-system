package com.demo.codigo_booking_system.modal.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String country;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Boolean enabled;
    private Boolean locked;
    private List<String> packages;

}
