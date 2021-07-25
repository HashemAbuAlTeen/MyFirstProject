package com.example.demo.User;

class UserNotFoundException extends RuntimeException {

    UserNotFoundException(int id) {
        super("Could not find employee " + id);
    }
}