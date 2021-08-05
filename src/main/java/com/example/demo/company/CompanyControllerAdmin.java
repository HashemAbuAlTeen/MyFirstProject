package com.example.demo.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This is a controller on Company with Admin APIs
 */
@RestController
public class CompanyControllerAdmin {

    @Autowired
    CompanyService companyService;

    /**
     * Post a Company
     *
     * @param companyDto the company dto
     * @return the company
     */
    @PostMapping("/admin/companies")
    public Company createCompany(@RequestBody CompanyDto companyDto){
        Company company = new Company(companyDto.getName(),companyDto.getLocation());
        return companyService.createCompany(company);
    }

    /**
     * PUT an existed company
     *
     *
     * @param companyDto the company dto
     * @return the company
     */
    @PutMapping("/admin/companies")
    public Company updateCompany(@RequestBody CompanyDto companyDto){
        Company company = new Company();
        company.setId(companyDto.getId());
        company.setLocation(companyDto.getLocation());
        company.setName(companyDto.getName());
        return companyService.updateCompany(company);
    }

    /**
     * Delete company.
     *
     * @param id the id
     */
    @DeleteMapping("/admin/companies/{id}")
    public void deleteCompany(@PathVariable int id){
        companyService.deleteCompany(id);
    }
}
