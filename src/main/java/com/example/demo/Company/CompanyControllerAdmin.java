package com.example.demo.Company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyControllerAdmin {

    @Autowired
    CompanyService companyService;

    @PostMapping("/admin/companies")
    Company createCompany(@RequestBody Company company){
        return companyService.createCompany(company);
    }

    @PutMapping("/admin/companies")
    Company updateCompany(@RequestBody Company company){
        return companyService.updateCompany(company);
    }

    @DeleteMapping("/admin/companies/{id}")
    void deleteCompany(@PathVariable int id ){
        companyService.deleteCompany(id);
    }
}
