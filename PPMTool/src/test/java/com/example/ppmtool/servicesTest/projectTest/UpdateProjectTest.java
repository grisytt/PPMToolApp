package com.example.ppmtool.servicesTest.projectTest;


import com.example.ppmtool.PpmToolApplication;
import com.example.ppmtool.domain.Project;
import com.example.ppmtool.domain.User;
import com.example.ppmtool.repositories.ProjectRepository;
import com.example.ppmtool.repositories.UserRepository;
import com.example.ppmtool.services.ProjectService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = PpmToolApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UpdateProjectTest {
    // update project successfully
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

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
    }

    @Test
    public void updateProjectTest() {
        Project project = new Project();
        project.setProjectIdentifier("junit");
        project.setProjectName("updateProjectTest");
        project.setDescription("update project successfully");
        project.setId(projectRepository.findByProjectIdentifier(project.getProjectIdentifier()).getId());

        projectService.saveOrUpdateProject(project, "testUser@email.com");
    }

    @After
    public void renew() {
        projectRepository.delete(projectRepository.findByProjectIdentifier("junit"));
        userRepository.delete(userRepository.findByUsername("testUser@email.com"));
    }
}