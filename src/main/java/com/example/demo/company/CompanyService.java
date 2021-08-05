package com.example.demo.company;

import com.example.demo.user.User;
import com.example.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for company
 */
@Service
public class CompanyService {

    @Autowired
    private final CompanyRepository repository;

    @Autowired
    private UserService userService;

    /**
     * Instantiates a new Company service.
     *
     * @param repository the repository
     */
    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }


    /**
     * Gets a list of all companies
     *
     * @return a List
     */
    public  List<Company> getAll() {
        return repository.findAll();
    }

    /**
     * Create a company
     *
     * @param company the company
     * @return the company
     */
    public Company createCompany(Company company) {
        return repository.save(company);
    }

    /**
     * check if a company existis
     *
     * @param id of the company
     * @return a boolean
     */
    public boolean exists(int id ) {
        return repository.existsById(id);
    }

    /**
     * Update an existed company
     * if the company doesnot exist it throws a
     *
     * @param updatedCompany the updated company CompanyNotFoundException
     * @return the updated company
     */
    public Company updateCompany(Company updatedCompany) {
        if(repository.existsById(updatedCompany.getId()))
            return repository.save(updatedCompany);
        else
            throw new CompanyNotFoundException(updatedCompany.getId());

    }

    /**
     * Delete a company.
     * if the company doesnot exist it throws CompanyNotFoundException
     *
     * @param id the id
     */
    public void deleteCompany(int id) {
        if(repository.existsById(id))
            repository.deleteById(id);
        else
            throw new CompanyNotFoundException(id );
    }

    /**
     * Gets a company by id
     * if the company doesnot exist it throws CompanyNotFoundException
     *
     * @param id the id
     * @return the by id
     */
    public Company getById(int id) {
        return repository.findById(id)
                .orElseThrow (() -> new CompanyNotFoundException(id) );
    }

    /**
     * Gets a company by user id
     * if the user CompanyId is null it throws UserDoesNotWorkInAnyCompanyException
     *
     * @param userId the user id
     * @return Company
     */
    public Company getByUserId(String userId) {
        User user = userService.getById(Integer.parseInt(userId));
        if(user.getCompany() == null ) throw new UserDoesNotWorkInAnyCompanyException(user);
        int companyId = user.getCompany().getId();
        return repository.findById(companyId)
                .orElseThrow (() -> new CompanyNotFoundException(companyId) );
    }

    /**
     * get a list of all companies if it was passed an empty String
     * or it gets the company for the user with the passed id
     *
     * @param userId the user id as a String
     * @return List of Companies
     */
    public List<Company> get(String userId) {
        if (userId.isBlank())
            return getAll();
        else {
            List<Company> list = new ArrayList<>();
            list.add(getByUserId(userId));
            return list;
        }
    }
}
