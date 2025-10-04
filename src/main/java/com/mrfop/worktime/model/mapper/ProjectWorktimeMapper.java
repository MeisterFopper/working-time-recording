package com.mrfop.worktime.model.mapper;

import com.mrfop.worktime.model.dto.ProjectWorktime;
import com.mrfop.worktime.model.entity.ProjectWorktimeEntity;

import org.springframework.stereotype.Component;

@Component
public class ProjectWorktimeMapper {

    public ProjectWorktime toDto(ProjectWorktimeEntity pw) {
        return new ProjectWorktime(
                pw.getId(),
                pw.getProject() != null ? pw.getProject().getId() : null,
                pw.getProject() != null ? pw.getProject().getName() : null,
                pw.getStartTime(),
                pw.getEndTime(),
                pw.getComment()
        );
    }
}