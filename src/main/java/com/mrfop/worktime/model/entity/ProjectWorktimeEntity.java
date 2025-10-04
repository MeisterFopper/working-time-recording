package com.mrfop.worktime.model.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_worktime")
@Data
public class ProjectWorktimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(length = 500)
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worktime_id", nullable = false)
    private WorktimeEntity worktime;

    public boolean isRunning() {
        return endTime == null;
    }

    public long getDurationInMinutes() {
        if (endTime == null) {
            return java.time.Duration.between(startTime, LocalDateTime.now()).toMinutes();
        }
        return java.time.Duration.between(startTime, endTime).toMinutes();
    }
}