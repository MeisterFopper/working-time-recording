package com.mrfop.worktime.controller.api;

import com.mrfop.worktime.model.dto.Worktime;
import com.mrfop.worktime.model.entity.ProjectWorktimeEntity;
import com.mrfop.worktime.model.entity.WorktimeEntity;
import com.mrfop.worktime.model.mapper.WorktimeMapper;
import com.mrfop.worktime.service.ProjectWorktimeService;
import com.mrfop.worktime.service.WorktimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/worktime")
@Tag(name = "Worktime API", description = "API for managing worktime entries")
public class WorktimeController {

    private final WorktimeService worktimeService;
    private final ProjectWorktimeService projectWorktimeService;
    private final WorktimeMapper worktimeMapper;

    public WorktimeController(WorktimeService worktimeService, ProjectWorktimeService projectWorktimeService, WorktimeMapper worktimeMapper) {
        this.worktimeService = worktimeService;
        this.projectWorktimeService = projectWorktimeService;
        this.worktimeMapper = worktimeMapper;
    }

    @Operation(summary = "Start the current workday (clock in)")
    @PostMapping("/start")
    public Worktime startDay() {
        return worktimeMapper.toDto(worktimeService.start(null));
    }

    @Operation(summary = "End the current workday (clock out)")
    @PostMapping("/stop")
    public Worktime stopDay() {
        Optional<WorktimeEntity> current = worktimeService.persistence().findCurrent();
        if (current.isPresent()) {
            Optional<ProjectWorktimeEntity> currentProjectTime = projectWorktimeService.persistence().findCurrent(current.get());
            if (currentProjectTime.isPresent()) {
                throw new IllegalArgumentException("Project worktime with ID " + currentProjectTime.get().getId() + " active. Please end stop first.");
            }
        }

        return worktimeMapper.toDto(worktimeService.stop(null));
    }

    @Operation(summary = "Get all workdays")
    @GetMapping("/all")
    public List<Worktime> getAllDays() {
        return worktimeService.persistence()
                .findAll()
                .stream()
                .map(worktimeMapper::toDto)
                .toList();
    }

    @Operation(summary = "Update start or end time of a workday")
    @PatchMapping("/{id}")
    public Worktime updateWorktime(@PathVariable Long id,
                                    @RequestParam(required = false) String startTime,
                                    @RequestParam(required = false) String endTime) {
        var worktime = worktimeService.persistence()
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Worktime with ID " + id + " not found"));

        if (startTime != null)
            worktime.setStartTime(LocalDateTime.parse(startTime));
        if (endTime != null)
            worktime.setEndTime(LocalDateTime.parse(endTime));

        return worktimeMapper.toDto(worktimeService.persistence().save(worktime));
    }
}