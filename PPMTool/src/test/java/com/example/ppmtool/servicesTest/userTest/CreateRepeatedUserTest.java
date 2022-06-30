package com.example.ppmtool.servicesTest.userTest;


import com.example.ppmtool.PpmToolApplication;
import com.example.ppmtool.domain.User;
import com.example.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.example.ppmtool.repositories.UserRepository;
import com.example.ppmtool.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = PpmToolApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CreateRepeatedUserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void preCreate() {
        User user = new User();
        user.setUsername("testUser@email.com");
        user.setPassword("NOT BLANK");
        user.setConfirmPassword("NOT BLANK");
        user.setFullName("NOT BLANK");
        userService.saveUser(user);
    }

    @Test
    public void createRepeatedUserTest() {
        User newUser = new User();
        newUser.setUsername("testUser@email.com");
        newUser.setPassword("NOT BLANK");
        newUser.setConfirmPassword("NOT BLANK");
        newUser.setFullName("NOT BLANK");
        exception.expect(UsernameAlreadyExistsException.class);
        exception.expectMessage("Username '" + newUser.getUsername() + "' already exists");

        userService.saveUser(newUser);
    }

    @After
    public void renew() {
        userRepository.delete(userRepository.findByUsername("testUser@email.com"));
    }
}