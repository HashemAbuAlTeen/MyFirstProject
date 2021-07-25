package com.example.demo.Company;

import com.example.demo.User.User;

public class UserDoesNotWorkInAnyCompanyException extends RuntimeException {
    public UserDoesNotWorkInAnyCompanyException(User user) {
        super("User " + user.getId() + " Does Not Work In Any Company");
    }
}
