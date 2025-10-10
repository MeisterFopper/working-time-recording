package com.mrfop.worktime.controller.api;

import com.mrfop.worktime.model.dto.ProjectWorktime;
import com.mrfop.worktime.model.entity.ProjectEntity;
import com.mrfop.worktime.model.entity.ProjectWorktimeEntity;
import com.mrfop.worktime.model.mapper.ProjectWorktimeMapper;
import com.mrfop.worktime.service.ProjectService;
import com.mrfop.worktime.service.ProjectWorktimeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/projecttime")
@Tag(name = "Project Worktime API", description = "Endpoints for managing project worktime entries")
public class ProjectWorktimeController {

    private final ProjectWorktimeService projectWorktimeService;
    private final ProjectService projectService;
    private final ProjectWorktimeMapper mapper;

    public ProjectWorktimeController(ProjectWorktimeService projectWorktimeService,
                                     ProjectService projectService,
                                     ProjectWorktimeMapper mapper) {
        this.projectWorktimeService = projectWorktimeService;
        this.projectService = projectService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get all project time entries")
    @GetMapping("/all")
    public List<ProjectWorktime> getAllProjects() {
        return projectWorktimeService.persistence()
                .findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Operation(summary = "Start tracking time for a project")
    @PostMapping("/{projectId}/start")
    public ProjectWorktime startProject(@PathVariable Long projectId) {
        ProjectEntity project = projectService.persistence()
                .findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + projectId + " not found."));
        
        return mapper.toDto(projectWorktimeService.startProject(project, null));
    }

    @Operation(summary = "Stop tracking time for a project")
    @PostMapping("/{projectWorktimeId}/stop")
    public ProjectWorktime stopProject(@PathVariable Long projectWorktimeId,
                                          @RequestParam(required = false) String comment) {
        ProjectEntity project = projectService.persistence()
                .findById(projectWorktimeId)
                .orElseThrow(() -> new IllegalArgumentException("Project worktime with ID " + projectWorktimeId + " not found."));

        return mapper.toDto(projectWorktimeService.stopProject(project, comment, null));
    }

    @Operation(summary = "Add or update comment for a project worktime entry")
    @PatchMapping("/{projectWorktimeId}/comment")
    public ProjectWorktime updateComment(
            @PathVariable Long projectWorktimeId,
            @RequestParam String comment) {

        ProjectWorktimeEntity pw = projectWorktimeService.persistence()
                .findById(projectWorktimeId)
                .orElseThrow(() ->
                        new IllegalArgumentException("ProjectWorktime with ID " + projectWorktimeId + " not found."));

        pw.setComment(comment);

        return mapper.toDto(projectWorktimeService.persistence().save(pw));
    }

    @Operation(summary = "Update start or end time of a workday")
    @PatchMapping("/{id}")
    public ProjectWorktime updateWorktime(@PathVariable Long id,
                                    @RequestParam(required = false) String startTime,
                                    @RequestParam(required = false) String endTime) {
        var worktime = projectWorktimeService.persistence()
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Worktime with ID " + id + " not found"));

        if (startTime != null)
            worktime.setStartTime(LocalDateTime.parse(startTime));
        if (endTime != null)
            worktime.setEndTime(LocalDateTime.parse(endTime));

        return mapper.toDto(projectWorktimeService.persistence().save(worktime));
    }
}