package com.mrfop.worktime.service;

import org.springframework.stereotype.Service;

import com.mrfop.worktime.model.entity.ProjectEntity;
import com.mrfop.worktime.model.entity.ProjectWorktimeEntity;
import com.mrfop.worktime.model.entity.WorktimeEntity;
import com.mrfop.worktime.persistence.ProjectWorktimePersistence;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProjectWorktimeService {

    private final ProjectWorktimePersistence persistence;
    private final WorktimeService worktimeService;

    public ProjectWorktimeService(ProjectWorktimePersistence persistence, WorktimeService worktimeService) {
        this.persistence = persistence;
        this.worktimeService = worktimeService;
    }

    public ProjectWorktimeEntity startProject(ProjectEntity project, LocalDateTime startTime) {
        Optional<WorktimeEntity> day = worktimeService.persistence().findCurrent();
        if (!day.isPresent()) {
            day = Optional.ofNullable(worktimeService.start(startTime != null ? startTime : LocalDateTime.now()));
        }

        Optional<ProjectWorktimeEntity> ongoing = persistence.findCurrent(day.get());
        if (!ongoing.isPresent()) {
            ProjectWorktimeEntity newWorktime = new ProjectWorktimeEntity();
            newWorktime.setProject(project);
            newWorktime.setStartTime(startTime != null ? startTime : LocalDateTime.now());
            newWorktime.setWorktime(day.get());
            return persistence.save(newWorktime);
        }
        throw new IllegalStateException("There is already an ongoing project worktime. Stop it before starting a new one.");
    }

    public ProjectWorktimeEntity stopProject(ProjectEntity project, String comment, LocalDateTime endTime) {
        Optional<WorktimeEntity> day = worktimeService.persistence().findCurrent();
        if (day.isPresent()) {
            Optional<ProjectWorktimeEntity> ongoing = persistence.findCurrent(day.get());
            if (ongoing.isPresent() && ongoing.get().getProject().equals(project)) {
                ongoing.get().setEndTime(endTime != null ? endTime : LocalDateTime.now());
                ongoing.get().setComment(comment);
                return persistence.save(ongoing.get());
            } else {
                throw new IllegalStateException("No ongoing project worktime found for the specified project.");
            }
        }
        throw new IllegalStateException("No ongoing workday found. Start a workday before stopping project time.");
    }

    public ProjectWorktimePersistence persistence() {
        return persistence;
    }
}