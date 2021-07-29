package com.example.demo.company;

import com.example.demo.user.User;

public class UserDoesNotWorkInAnyCompanyException extends RuntimeException {
    public UserDoesNotWorkInAnyCompanyException(User user) {
        super("User " + user.getId() + " Does Not Work In Any Company");
    }
}
