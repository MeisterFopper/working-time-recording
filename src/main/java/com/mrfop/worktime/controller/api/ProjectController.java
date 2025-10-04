package com.mrfop.worktime.controller.api;

import com.mrfop.worktime.model.dto.Project;
import com.mrfop.worktime.model.entity.ProjectEntity;
import com.mrfop.worktime.model.mapper.ProjectMapper;
import com.mrfop.worktime.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@Tag(name = "Project Management API", description = "Manage projects for time tracking")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @Operation(summary = "Get all project entries")
    @GetMapping("/all")
    public List<Project> getAllProjects() {
        return projectService.persistence()
                .findAll()
                .stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Operation(summary = "Get all active project entries")
    @GetMapping("/active")
    public List<Project> getAllActiveProjects() {
        return projectService.persistence()
                .getActiveProjects()
                .stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Operation(summary = "Create a new project")
    @PostMapping("/create")
    public Project createProject(@RequestParam String projectName) {
        ProjectEntity project = projectService.create(projectName);
        return projectMapper.toDto(project);
    }

    @Operation(summary = "Activate a project")
    @PostMapping("/{projectId}/activate")
    public Project activateProject(@PathVariable Long projectId) {
        ProjectEntity project = projectService.persistence()
                .findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + projectId + " not found."));
        return projectMapper.toDto(projectService.activate(project));
    }

    @Operation(summary = "Deactivate a project")
    @PostMapping("/{projectId}/deactivate")
    public Project deactivateProject(@PathVariable Long projectId) {
        ProjectEntity project = projectService.persistence()
                .findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + projectId + " not found."));
        return projectMapper.toDto(projectService.deactivate(project));
    }

    @Operation(summary = "Rename a project")
    @PatchMapping("/{projectId}/rename")
    public Project renameProject(@PathVariable Long projectId,
                                 @RequestParam String name) {
        ProjectEntity project = projectService.persistence()
                .findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + projectId + " not found."));
        return projectMapper.toDto(projectService.rename(project, name));
    }

    @Operation(summary = "Update project description")
    @PatchMapping("/{projectId}/description")
    public Project updateDescription(@PathVariable Long projectId,
                                     @RequestParam String description) {
        ProjectEntity project = projectService.persistence()
                .findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + projectId + " not found."));
        return projectMapper.toDto(projectService.updateDescription(project, description));
    }
}