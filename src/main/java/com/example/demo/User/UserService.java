package com.example.demo.User;

import com.example.demo.Company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final UserDao userDao;

    public UserService(UserRepository repository, UserDao userDao) {
        this.repository = repository;
        this.userDao = userDao;
    }



    public User createUserInACompany(User newUser, int companyId) {
        newUser.setCompany(new Company(companyId));
        return repository.save(newUser);
    }

    public User updateUser(User updatedUser) {
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

    public void deleteUser(int id) {
        repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id) );
        repository.deleteById(id);
    }

    public List<User> searchUsers(String id, String firstName, String lastName, String age) {

        return userDao.findUserByIdAndFirstNameAndLastNameAndAgeAndCompany_Id(id, firstName , lastName, age);

    }

    public User getById(int id) {
        return repository.findById(id)
                .orElseThrow( () -> new UserNotFoundException(id) );

    }

    public User createUser(User newUser) {
        return repository.save(newUser);
    }

    public List<User> getCompanyUsers(int companyId) {
        return repository.findByCompany_Id(companyId);
    }


    public List<User> generalSearch(String id, String firstName, String lastName, String age,
                                    String companyId, String companyName, String companyLocation) {
        return userDao.generalUserSearch(id, firstName, lastName, age, companyId, companyName, companyLocation);
    }
}
