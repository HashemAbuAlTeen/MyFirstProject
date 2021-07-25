package com.example.demo.User;

import com.example.demo.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>{

    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    List<User> findByAge(int age);

    List<User> findByCompany_Id(int companyId);







}
