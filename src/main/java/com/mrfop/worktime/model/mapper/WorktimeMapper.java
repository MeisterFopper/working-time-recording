package com.mrfop.worktime.model.mapper;

import com.mrfop.worktime.model.dto.ProjectWorktime;
import com.mrfop.worktime.model.dto.Worktime;
import com.mrfop.worktime.model.entity.WorktimeEntity;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorktimeMapper {

    private final ProjectWorktimeMapper projectWorktimeMapper;

    public WorktimeMapper(ProjectWorktimeMapper projectWorktimeMapper) {
        this.projectWorktimeMapper = projectWorktimeMapper;
    }

    public Worktime toDto(WorktimeEntity w) {
        List<ProjectWorktime> projectDtos = w.getProjectWorktimes()
                .stream()
                .map(projectWorktimeMapper::toDto)
                .toList();

        return new Worktime(
                w.getId(),
                w.getStartTime(),
                w.getEndTime(),
                projectDtos
        );
    }
}