package com.example.demo.Company;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(int id) {
        super("Could not found company "+ id);
    }
}
