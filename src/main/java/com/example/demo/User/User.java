package com.example.demo.User;

import com.example.demo.Company.Company;

import javax.persistence.*;



@Entity
public class User {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        int id;
    private String firstName;
    private String lastName;
    private int age;
    @ManyToOne
    private Company company;



    public User(String firstName, String lastName, int age) {
        this.lastName = lastName;
        this.age = age;
        this.firstName = firstName;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }



}
