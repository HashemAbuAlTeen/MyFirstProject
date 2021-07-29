package com.example.demo.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserControllerAdmin {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/hi")
    public String welcome(){
        return "Welcome admin";
    }

    @PostMapping("/admin/companies/{companyId}/users")
    public User newUser(@RequestBody UserDto userDto, @PathVariable int companyId) {
        User user = new User();
        user.setAge(userDto.getAge());
        user.setCompany(userDto.getCompany());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setId(userDto.getId());
        return userService.createUserInACompany(user, companyId);
    }

    @PostMapping("/admin/users")
    public User newUser(@RequestBody UserDto userDto){
        User user = new User();
        user.setAge(userDto.getAge());
        user.setCompany(userDto.getCompany());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setId(userDto.getId());
        return userService.createUser(user);
    }

    @PutMapping("/admin/users")
    public User updateUser(@RequestBody UserDto userDto) {
        User user  = new User();
        user.setAge(userDto.getAge());
        user.setCompany(userDto.getCompany());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setId(userDto.getId());
        return userService.updateUser(user);

    }
    @DeleteMapping("/admin/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
