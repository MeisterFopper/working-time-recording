package com.mrfop.worktime.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrfop.worktime.model.entity.ProjectWorktimeEntity;

@Repository
public interface ProjectWorktimeRepository extends JpaRepository<ProjectWorktimeEntity, Long> {

    Optional<ProjectWorktimeEntity> findTopByEndTimeIsNullAndWorktimeIdOrderByStartTimeDesc(Long worktimeId);
}