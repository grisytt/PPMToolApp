package com.example.ppmtool.servicesTest.projectTaskTest;


import com.example.ppmtool.PpmToolApplication;
import com.example.ppmtool.domain.Backlog;
import com.example.ppmtool.domain.Project;
import com.example.ppmtool.domain.ProjectTask;
import com.example.ppmtool.domain.User;
import com.example.ppmtool.repositories.BacklogRepository;
import com.example.ppmtool.repositories.ProjectRepository;
import com.example.ppmtool.repositories.ProjectTaskRepository;
import com.example.ppmtool.repositories.UserRepository;
import com.example.ppmtool.services.ProjectTaskService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = PpmToolApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AddProjectTaskTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

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
        Backlog backlog = new Backlog();
        project.setBacklog(backlog);
        backlog.setProject(project);
        backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
        backlogRepository.save(backlog);
    }

    @Test
    public void addProjectTaskTest() {
        ProjectTask projectTask = new ProjectTask();
        projectTask.setSummary("Test for adding project task");
        projectTaskService.addProjectTask("junit", projectTask, "testUser@email.com");
    }

    @After
    public void renew() {
        projectTaskRepository.delete(projectTaskRepository.findByProjectSequence("JUNIT-1"));
        projectRepository.delete(projectRepository.findByProjectIdentifier("JUNIT"));
        userRepository.delete(userRepository.findByUsername("testUser@email.com"));
    }
}