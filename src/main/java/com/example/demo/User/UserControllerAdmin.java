package com.example.demo.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserControllerAdmin {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/hi")
    String welcome(){
        return "Welcome admin";
    }

    @PostMapping("/admin/companies/{companyId}/users")
    User newUser(@RequestBody User newUser, @PathVariable int companyId) {
        return userService.createUserInACompany(newUser, companyId);
    }

    @PostMapping("/admin/users")
    User newUser(@RequestBody User newUser){
        return userService.createUser(newUser);
    }

    @PutMapping("/admin/users")
    User updateUser(@RequestBody User user ) {
        return userService.updateUser(user);

    }
    @DeleteMapping("/admin/users/{id}")
    void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
