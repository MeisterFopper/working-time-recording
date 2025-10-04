package com.mrfop.worktime.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public record Worktime(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        List<ProjectWorktime> projectWorktimes
) {}