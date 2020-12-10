package com.netcracker.taskmanager.controller;

import com.netcracker.taskmanager.entity.Project;
import com.netcracker.taskmanager.entity.User;
import com.netcracker.taskmanager.security.JwtProvider;
import com.netcracker.taskmanager.service.ProjectService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final JwtProvider jwtProvider;

    @GetMapping("/projects")
    public List<Project> getProjects() {
        return projectService.findAll();
    }

    @GetMapping("/project/{project}")
    public Project getProject(@PathVariable Project project) {
        if (project == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        return project;
    }

    //TODO: validation, update, task add
    @PostMapping("/project/create")
    public Project createProject(@RequestBody Project project) {
        return projectService.save(project);
    }

    @DeleteMapping("/project/{project}/delete")
    @ResponseStatus(value = HttpStatus.OK, reason = "Order is created")
    public void deleteProject(@PathVariable Project project) {
        if (project == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        projectService.delete(project);
    }

    @PostMapping("/project/{project}/end")
    public Project endProject(@PathVariable Project project) {
        if (project == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        if (!project.getActive()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project is already ended");
        project.setActive(false);
        return projectService.save(project);
    }

    @PostMapping("/project/{project}/invite")
    public InviteResponse invite(@PathVariable Project project, @RequestBody(required = false) Integer days) {
        if (project == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        String href = "http://localhost:8888/project/join?invite=" + jwtProvider.generateHref(project.getId(), days == null ? 5 : days);
        return new InviteResponse(href);
    }

    @GetMapping("/project/join")
    public Project join(@AuthenticationPrincipal User user, @RequestParam String invite){
        if (user == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You must be authorized");
        String id = jwtProvider.getProjectFromHref(invite);
        Project project = projectService.findById(id);
        if (project == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid href");
        return project;
    }

    @Data
    @RequiredArgsConstructor
    private static class InviteResponse {
        private final String href;
    }

}
