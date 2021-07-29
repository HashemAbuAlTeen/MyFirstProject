package com.example.demo.company;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id = 0;
    private String name;
    private String location;

    public Company(int id){
        this.id = id;
    }
    public Company(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Company() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
