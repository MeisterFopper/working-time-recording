package com.mrfop.worktime.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrfop.worktime.model.entity.ProjectEntity;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    Optional<ProjectEntity> findByNameIgnoreCase(String name);
    
    List<ProjectEntity> findByActiveTrueOrderByNameAsc();
}