package com.example.ppmtool.servicesTest.userTest;

import com.example.ppmtool.PpmToolApplication;
import com.example.ppmtool.domain.User;
import com.example.ppmtool.repositories.UserRepository;
import com.example.ppmtool.services.UserService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = PpmToolApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CreateNewUserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void createNewUserTest() {
        User user = new User();
        user.setUsername("testUser@email.com");
        user.setPassword("NOT BLANK");
        user.setConfirmPassword("NOT BLANK");
        user.setFullName("NOT BLANK");
        userService.saveUser(user);
    }

    @After
    public void renew() {
        userRepository.delete(userRepository.findByUsername("testUser@email.com"));
    }
}