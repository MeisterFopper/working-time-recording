package com.mrfop.worktime.model.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worktime")
@Data
public class WorktimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "worktime", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProjectWorktimeEntity> projectWorktimes = new ArrayList<>();

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