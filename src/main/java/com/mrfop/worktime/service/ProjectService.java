package com.mrfop.worktime.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mrfop.worktime.model.entity.ProjectEntity;
import com.mrfop.worktime.persistence.ProjectPersistence;

@Service
public class ProjectService {

    private final ProjectPersistence persistence;

    public ProjectService(ProjectPersistence persistence) {
        this.persistence = persistence;
    }

    public ProjectEntity create(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Project name cannot be null or blank");
        }
        Optional<ProjectEntity> project = persistence.getByName(name);
        if (project.isPresent()) {
            return project.get();
        } else {
            ProjectEntity newProject = new ProjectEntity();
            newProject.setName(name);
            return persistence.save(newProject);
        }
    }

    public ProjectEntity activate(ProjectEntity project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }

        if (project.isActive()) {
            return project;
        }

        project.setActive(true);
        return persistence.save(project);
    }

    public ProjectEntity deactivate(ProjectEntity project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }

        if (!project.isActive()) {
            return project;
        }

        project.setActive(false);
        return persistence.save(project);
    }

    public ProjectEntity rename(ProjectEntity p, String name) {
        p.setName(name);
        return persistence().save(p);
    }

    public ProjectEntity updateDescription(ProjectEntity p, String description) {
        p.setDescription(description);
        return persistence().save(p);
    }

    public ProjectPersistence persistence() {
        return persistence;
    }
}