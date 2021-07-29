package com.example.demo.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyControllerAdmin {

    @Autowired
    CompanyService companyService;

    @PostMapping("/admin/companies")
    public Company createCompany(@RequestBody CompanyDto companyDto){
        Company company = new Company(companyDto.getName(),companyDto.getLocation());
        return companyService.createCompany(company);
    }

    @PutMapping("/admin/companies")
    public Company updateCompany(@RequestBody CompanyDto companyDto){
        Company company = new Company();
        company.setId(companyDto.getId());
        company.setLocation(companyDto.getLocation());
        company.setName(companyDto.getName());
        return companyService.updateCompany(company);
    }

    @DeleteMapping("/admin/companies/{id}")
    public void deleteCompany(@PathVariable int id){
        companyService.deleteCompany(id);
    }
}
