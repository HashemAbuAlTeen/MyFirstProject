package com.example.demo.company;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(int id) {
        super("Could not found company "+ id);
    }
}
