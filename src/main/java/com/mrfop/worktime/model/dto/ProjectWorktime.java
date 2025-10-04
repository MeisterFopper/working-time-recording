package com.mrfop.worktime.model.dto;

import java.time.LocalDateTime;

public record ProjectWorktime(
        Long id,
        Long projectId,
        String projectName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String comment
) {}