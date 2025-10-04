package com.mrfop.worktime.model.dto;

public record Project(
        Long id,
        String name,
        String description,
        boolean active
) {}