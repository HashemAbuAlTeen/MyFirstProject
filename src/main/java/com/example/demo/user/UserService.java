package com.example.demo.user;

import com.example.demo.company.Company;
import com.example.demo.company.CompanyNotFoundException;
import com.example.demo.company.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private final UserRepository repository;

    @Autowired
    private final UserDao userDao;

    @Autowired
    private final CompanyRepository companyRepository;

    /**
     * Instantiates a new User service.
     *  @param repository the repository
     * @param userDao    the user dao
     * @param companyRepository repository for company object
     */
    public UserService(UserRepository repository, UserDao userDao, CompanyRepository companyRepository) {
        this.repository = repository;
        this.userDao = userDao;
        this.companyRepository = companyRepository;
    }


    /**
     * Create user in a company .
     *
     * @param newUser   the new user
     * @param companyId the company id
     * @return the user
     */
    public User createUserInACompany(User newUser, int companyId) {
        logger.info("createUserInACompany Method has been accessed");
        if(!companyRepository.existsById(companyId))
            throw new CompanyNotFoundException(companyId);

        newUser.setCompany(new Company(companyId));
        return repository.save(newUser);
    }

    /**
     * Update user user.
     *
     * @throws UserNotFoundException throws it if user does not exist
     *
     * @param updatedUser the updated user
     * @return the user
     */
    public User updateUser(User updatedUser) {
        logger.info("updateUser Method has been accessed");
        User user =  repository.findById(updatedUser.getId())
                .orElseThrow(() -> new UserNotFoundException(updatedUser.getId()));

        if(updatedUser.getFirstName() != null )
            user.setFirstName(updatedUser.getFirstName());
        if(updatedUser.getLastName() != null)
            user.setLastName(updatedUser.getLastName());
        if(updatedUser.getAge() != 0)
            user.setAge(updatedUser.getAge());

        return repository.save(user);
    }

    /**
     * Delete user.
     *
     * @throws UserNotFoundException if user does not exist
     *
     * @param id the id
     */
    public void deleteUser(int id) {
        logger.info("deleteUser Method has been accessed");
        if(repository.existsById(id))
            repository.deleteById(id);
        else
            throw new UserNotFoundException(id);
    }

    /**
     * Search users list.
     *
     * @param id        the id
     * @param firstName the first name
     * @param lastName  the last name
     * @param age       the age
     * @return the list
     */
    public List<User> searchUsers(String id, String firstName, String lastName, String age) {
        logger.info("SearchUsers Method has been accessed");
        return userDao.findUserByIdAndFirstNameAndLastNameAndAgeAndCompanyId(id, firstName , lastName, age);

    }

    /**
     * Gets by id.
     *
     * @throws UserNotFoundException if user does not exist
     *
     * @param id the id
     * @return the User
     */
    public User getById(int id) {
        logger.info("getById Method has been accessed");
        return repository.findById(id)
                .orElseThrow( () -> new UserNotFoundException(id) );

    }

    /**
     * Create user.
     *
     * @param newUser the new user
     * @return the user
     */
    public User createUser(User newUser) {
        logger.info("createUser Method has been accessed");
        return repository.save(newUser);
    }

    /**
     * Gets company users.
     *
     * @param companyId the company id
     * @return the company users
     */
    public List<User> getCompanyUsers(int companyId) {
        logger.info("getCompanyUsers Method has been accessed");
        return repository.findByCompanyId(companyId);
    }


    /**
     * General search list.
     *
     * @param id              the id
     * @param firstName       the first name
     * @param lastName        the last name
     * @param age             the age
     * @param companyId       the company id
     * @param companyName     the company name
     * @param companyLocation the company location
     * @return the list
     */
    public List<User> generalSearch(String id, String firstName, String lastName, String age,
                                    String companyId, String companyName, String companyLocation) {
        logger.info("GeneralSearch Method has been accessed");
        return userDao.generalUserSearch(id, firstName, lastName, age, companyId, companyName, companyLocation);
    }
}
