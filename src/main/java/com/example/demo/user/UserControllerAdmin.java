package com.example.demo.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for admin access
 */
@RestController
public class UserControllerAdmin {


    Logger logger = LoggerFactory.getLogger(UserControllerAdmin.class);

    @Autowired
    private UserService userService;

    /**
     * Welcome string.
     *
     * @return the string
     */
    @GetMapping("/admin/hi")
    public String welcome(){
        return "Welcome admin";
    }

    /**
     * Create user in a company.
     *
     * @param userDto   the user dto
     * @param companyId the company id
     * @return the user
     */
    @PostMapping("/admin/companies/{companyId}/users")
    public User createUserInACompany(@RequestBody UserDto userDto, @PathVariable int companyId) {
        logger.info("createUserInACompany Method is accessed ");
        User user = new User();
        if(userDto.getAge() == 0)
        {
            logger.warn("The new user age is 0");
        }
        if(userDto.getFirstName() == null)
            logger.warn("The new user first name is null");
        if(userDto.getLastName() == null )
            logger.warn("The new user last name is null");

        user.setAge(userDto.getAge());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setId(userDto.getId());
        return userService.createUserInACompany(user, companyId);
    }

    /**
     * Create user.
     *
     * @param userDto the user dto
     * @return the user
     */
    @PostMapping("/admin/users")
    public User createUser(@RequestBody UserDto userDto){
        logger.info("createUser method has been accessed ");
        if(userDto.getAge() == 0)
        {
            logger.warn("The new user age is 0");
        }
        if(userDto.getFirstName() == null)
            logger.warn("The new user first name is null");
        if(userDto.getLastName() == null )
            logger.warn("The new user last name is null");
        User user = new User();
        user.setAge(userDto.getAge());
        user.setCompany(userDto.getCompany());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setId(userDto.getId());
        return userService.createUser(user);
    }

    /**
     * Update user user.
     *
     * @param userDto the user dto
     * @return the user
     */
    @PutMapping("/admin/users")
    public User updateUser(@RequestBody UserDto userDto) {
        logger.info("updateUser method has been accessed ");
        User user  = new User();
        user.setAge(userDto.getAge());
        user.setCompany(userDto.getCompany());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setId(userDto.getId());
        return userService.updateUser(user);

    }

    /**
     * Delete user.
     *
     * @param id the id
     */
    @DeleteMapping("/admin/users/{id}")
    public void deleteUser(@PathVariable int id) {
        logger.info("deleteUser method has been accessed ");
        userService.deleteUser(id);
    }
}
