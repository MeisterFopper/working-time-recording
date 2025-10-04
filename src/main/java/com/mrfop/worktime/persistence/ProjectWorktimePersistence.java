package com.mrfop.worktime.persistence;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.mrfop.worktime.model.entity.ProjectWorktimeEntity;
import com.mrfop.worktime.model.entity.WorktimeEntity;
import com.mrfop.worktime.repository.ProjectWorktimeRepository;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectWorktimePersistence {

    private final ProjectWorktimeRepository repo;

    public ProjectWorktimePersistence(ProjectWorktimeRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "projectWorktimes", key = "'all'")
    public List<ProjectWorktimeEntity> findAll() {
        return repo.findAll();
    }

    @Cacheable(value = "projectWorktimes", key = "#worktime.id")
    public Optional<ProjectWorktimeEntity> findCurrent(WorktimeEntity worktime) {
        return repo.findTopByEndTimeIsNullAndWorktimeIdOrderByStartTimeDesc(worktime.getId());
    }

    @Cacheable(value = "projectWorktimes", key = "#id")
    public Optional<ProjectWorktimeEntity> findById(Long id) {
        return repo.findById(id);
    }

    @CacheEvict(value = { "projectWorktimes", "worktimes" }, allEntries = true)
    public ProjectWorktimeEntity save(ProjectWorktimeEntity projectWorktime) {
        return repo.save(projectWorktime);
    }

    @CacheEvict(value = { "projectWorktimes", "worktimes" }, allEntries = true)
    public void delete(ProjectWorktimeEntity projectWorktime) {
        repo.delete(projectWorktime);
    }
}