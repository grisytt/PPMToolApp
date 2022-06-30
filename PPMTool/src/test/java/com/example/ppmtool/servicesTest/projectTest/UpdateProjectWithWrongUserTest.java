package com.example.ppmtool.servicesTest.projectTest;


import com.example.ppmtool.PpmToolApplication;
import com.example.ppmtool.domain.Project;
import com.example.ppmtool.domain.User;
import com.example.ppmtool.exceptions.ProjectNotFoundException;
import com.example.ppmtool.repositories.ProjectRepository;
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
public class UpdateProjectWithWrongUserTest {
    // update project with the wrong user
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

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
        Project project = new Project();
        project.setProjectIdentifier("junit");
        project.setProjectName("NOT BLANK");
        project.setDescription("NOT BLANK");
        project.setProjectLeader("testUser@email.com");
        projectRepository.save(project);

        User user2 = new User();
        user2.setUsername("user2@email.com");
        user2.setPassword("NOT BLANK");
        user2.setConfirmPassword("NOT BLANK");
        user2.setFullName("NOT BLANK");
        userRepository.save(user2);
    }

    @Test
    public void updateProjectWithWrongUserTest() {
        Project project = new Project();
        project.setProjectIdentifier("junit");
        project.setProjectName("updateProjectWithWrongUser");
        project.setDescription("Test for updating project with wrong user");
        project.setId(projectRepository.findByProjectIdentifier(project.getProjectIdentifier()).getId());

        exception.expect(ProjectNotFoundException.class);
        exception.expectMessage("Project Not Found in Your Account");

        projectService.saveOrUpdateProject(project, "user2@email.com");
    }

    @After
    public void renew() {
        projectRepository.delete(projectRepository.findByProjectIdentifier("junit"));
        userRepository.delete(userRepository.findByUsername("testUser@email.com"));
        userRepository.delete(userRepository.findByUsername("user2@email.com"));
    }
}