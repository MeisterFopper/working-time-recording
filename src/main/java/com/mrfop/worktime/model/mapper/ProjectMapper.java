package com.mrfop.worktime.model.mapper;

import com.mrfop.worktime.model.dto.Project;
import com.mrfop.worktime.model.entity.ProjectEntity;

import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toDto(ProjectEntity p) {
        return new Project(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.isActive()
        );
    }
}