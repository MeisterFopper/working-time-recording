package com.mrfop.worktime.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrfop.worktime.model.entity.WorktimeEntity;

@Repository
public interface WorktimeRepository extends JpaRepository<WorktimeEntity, Long> {
    
    List<WorktimeEntity> findAllByOrderByStartTimeDesc();

    Optional<WorktimeEntity> findTopByEndTimeIsNullOrderByStartTimeDesc();
}