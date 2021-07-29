package com.example.demo.user;

class UserNotFoundException extends RuntimeException {

    UserNotFoundException(int id) {
        super("Could not find employee " + id);
    }
}