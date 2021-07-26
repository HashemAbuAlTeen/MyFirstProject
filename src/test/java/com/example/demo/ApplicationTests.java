package com.example.demo;

import com.example.demo.User.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ApplicationTests {

    @Autowired
    UserController userController;

    @Test
    public void contextLoads()  {
        assertThat(userController).isNotNull();
    }

    @Test
    @DisplayName("1 + 1 = 2")
    void addsTwoNumbers() {

        assertEquals(2, 1+1, "1 + 1 should equal 2");
    }







}
