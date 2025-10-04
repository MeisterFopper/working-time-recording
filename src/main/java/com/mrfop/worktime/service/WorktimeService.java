package com.mrfop.worktime.service;

import com.mrfop.worktime.model.entity.WorktimeEntity;
import com.mrfop.worktime.persistence.WorktimePersistence;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WorktimeService {

    private final WorktimePersistence persistence;

    public WorktimeService(WorktimePersistence persistence) {
        this.persistence = persistence;
    }

    public WorktimeEntity start(LocalDateTime startTime) {
        Optional<WorktimeEntity> current = persistence.findCurrent();
        if (current.isEmpty()) {
            WorktimeEntity newWorktime = new WorktimeEntity();
            newWorktime.setStartTime(startTime != null ? startTime : LocalDateTime.now());
            return persistence.save(newWorktime);
        } else {
            throw new IllegalStateException("A worktime entry is already running.");
        }
    }

    public WorktimeEntity stop(LocalDateTime endTime) {
        Optional<WorktimeEntity> ongoing = persistence.findCurrent();
        if (ongoing.isPresent()) {
            ongoing.get().setEndTime(endTime != null ? endTime : LocalDateTime.now());
            return persistence.save(ongoing.get());
        } else {
            throw new IllegalStateException("No ongoing worktime entry to stop.");
        }
    }

    public boolean isRunning() {
        return persistence.findCurrent().isPresent();
    }

    public WorktimePersistence persistence() {
        return persistence;
    }
}