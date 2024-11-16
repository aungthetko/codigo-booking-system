package com.demo.codigo_booking_system.exception.custom;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){
        super(message);
    }
}
