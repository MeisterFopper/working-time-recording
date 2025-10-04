package com.mrfop.worktime.persistence;

import com.mrfop.worktime.model.entity.WorktimeEntity;
import com.mrfop.worktime.repository.WorktimeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class WorktimePersistence {

    private final WorktimeRepository repo;

    public WorktimePersistence(WorktimeRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "worktimes", key = "'all'")
    public List<WorktimeEntity> findAll() {
        return repo.findAllByOrderByStartTimeDesc();
    }

    @Cacheable(value = "worktimes", key = "'current'")
    public Optional<WorktimeEntity> findCurrent() {
        return repo.findTopByEndTimeIsNullOrderByStartTimeDesc();
    }

    @Cacheable(value = "worktimes", key = "#id")
    public Optional<WorktimeEntity> findById(Long id) {
        return repo.findById(id);
    }

    @CacheEvict(value = "worktimes", allEntries = true)
    public WorktimeEntity save(WorktimeEntity worktime) {
        return repo.save(worktime);
    }

    @CacheEvict(value = "worktimes", allEntries = true)
    public void delete(WorktimeEntity worktime) {
        repo.delete(worktime);
    }
}