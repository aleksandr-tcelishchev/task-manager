package com.netcracker.taskmanager.service;

import com.netcracker.taskmanager.entity.Project;
import com.netcracker.taskmanager.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }

    public Project findById(String id) {
        return projectRepository.findById(id).orElse(null);
    }
}
