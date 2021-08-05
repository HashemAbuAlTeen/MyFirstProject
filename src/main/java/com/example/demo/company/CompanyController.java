package com.example.demo.company;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Controler for company entity
 */
@RestController
public class CompanyController {

    /**
     * The Company service.
     */
    @Autowired
    CompanyService companyService;

    /**
     * Get List of all companies or
     * Get a company for a specific user Id
     *
     * @param userId the user id
     * @return the list
     */
    @GetMapping("/companies")
    public List<Company> get(@RequestParam(name = "userId" , required = false , defaultValue = "") String userId) {
        return companyService.get(userId);
    }


    /**
     * Get a company by its Id
     *
     * @param id the id
     * @return the company
     */
    @GetMapping("/companies/{id}")
    public Company getById(@PathVariable int id){
        return companyService.getById(id);
    }




}
