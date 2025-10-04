package com.mrfop.worktime.persistence;

import com.mrfop.worktime.model.entity.ProjectEntity;
import com.mrfop.worktime.repository.ProjectRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectPersistence {

    private final ProjectRepository repo;

    public ProjectPersistence(ProjectRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "projects", key = "'all'")
    public List<ProjectEntity> findAll() {
        return repo.findAll();
    }

    @Cacheable(value = "activeProjects", key = "'active'")
    public List<ProjectEntity> getActiveProjects() {
        return repo.findByActiveTrueOrderByNameAsc();
    }

    @Cacheable(value = "projects", key = "#id")
    public Optional<ProjectEntity> findById(Long id) {
        return repo.findById(id);
    }

    @Cacheable(value = "projects", key = "#name?.toLowerCase()")
    public Optional<ProjectEntity> getByName(String name) {
        return repo.findByNameIgnoreCase(name);
    }

    @CacheEvict(value = { "activeProjects", "projects" }, allEntries = true)
    public ProjectEntity save(ProjectEntity project) {
        return repo.save(project);
    }

    @CacheEvict(value = { "activeProjects", "projects" }, allEntries = true)
    public void delete(ProjectEntity project) {
        repo.delete(project);
    }
}