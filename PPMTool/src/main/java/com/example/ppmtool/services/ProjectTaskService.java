package com.example.ppmtool.services;


import com.example.ppmtool.domain.Backlog;
import com.example.ppmtool.domain.Project;
import com.example.ppmtool.domain.ProjectTask;
import com.example.ppmtool.exceptions.ProjectIdException;
import com.example.ppmtool.exceptions.ProjectNotFoundException;
import com.example.ppmtool.repositories.BacklogRepository;
import com.example.ppmtool.repositories.ProjectRepository;
import com.example.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

        // PTs to be added to a specific project, project != null, BL exists
        Backlog backlog = projectService.findByProjectIdentifier(projectIdentifier.toUpperCase(), username).getBacklog();
        // set the BL to PT
        projectTask.setBacklog(backlog);
        // we want to have our project sequence to be like this IDPRO-1 IDPRO-2
        Integer backlogSequence = backlog.getPTSequence();

        // Update the BL SEQUENCE
        backlogSequence++;
        backlog.setPTSequence(backlogSequence);

        // Add sequence to project task
        projectTask.setProjectSequence(projectIdentifier.toUpperCase() + "-" + backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier.toUpperCase());

        // INITIAL statue when status null
        if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
            projectTask.setStatus("TO_DO");
        }

        // INITIAL priority when priority null
        if (projectTask.getPriority() == null || projectTask.getPriority() == 0) { // In the future we need projectTask.getPriority() == 0 to handle the form
            projectTask.setPriority(3);
        }

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id, String username) {

        projectService.findByProjectIdentifier(id, username);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {

        // make sure we are searching on the right backlog
//        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
//        if (backlog == null) {
//            throw new ProjectNotFoundException("Project with ID '" + backlog_id + "' does not exist");
//        }
        projectService.findByProjectIdentifier(backlog_id, username);

        // make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task '" + pt_id + "' does not found");
        }

        // make sure that the backlog/project id in the path corresponds to the right project
        if (!projectTask.getProjectIdentifier().toUpperCase().equals(backlog_id.toUpperCase())) {
            throw new ProjectNotFoundException("Project Task '" + pt_id + "' does not exist in the '" + backlog_id + "'");
        }

        return projectTask;
    }


    // Update project task
    // find existing project task
    // replace it with updated task
    // save update
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username) {
//        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);

    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
//        Backlog backlog = projectTask.getBacklog();
//
//        List<ProjectTask> projectTaskList = backlog.getProjectTasks();
//        projectTaskList.remove(projectTask);
//        backlogRepository.save(backlog);

        projectTaskRepository.delete(projectTask);
    }

}