package com.example.ppmtool.servicesTest.projectTest;


import com.example.ppmtool.PpmToolApplication;
import com.example.ppmtool.domain.User;
import com.example.ppmtool.exceptions.ProjectIdException;
import com.example.ppmtool.repositories.UserRepository;
import com.example.ppmtool.services.ProjectService;
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
public class FindProjectNotExistTest {
    // test for "project does not exist"
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void preCreate() {
        User user = new User();
        user.setUsername("testUser@email.com");
        user.setPassword("NOT BLANK");
        user.setConfirmPassword("NOT BLANK");
        user.setFullName("NOT BLANK");
        userRepository.save(user);
    }

    @Test
    public void projectNotExistTest() {
        String projectIdentifier = "junit";
        exception.expect(ProjectIdException.class);
        exception.expectMessage("Project ID '" + projectIdentifier + "' does not exists");

        projectService.findByProjectIdentifier(projectIdentifier, "testUser@email.com");
    }

    @After
    public void renew() {
        userRepository.delete(userRepository.findByUsername("testUser@email.com"));
    }
}