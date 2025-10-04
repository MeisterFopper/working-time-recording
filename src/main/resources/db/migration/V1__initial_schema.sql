-- Worktime Tracking System Initial Schema
CREATE TABLE IF NOT EXISTS worktime (
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    start_time   DATETIME NOT NULL,
    end_time     DATETIME NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS project (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL UNIQUE,
    active      BOOLEAN NOT NULL DEFAULT TRUE,
    description VARCHAR(500)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS project_worktime (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    worktime_id     BIGINT NOT NULL,
    project_id      BIGINT NOT NULL,
    start_time      DATETIME NOT NULL,
    end_time        DATETIME NULL,
    comment         VARCHAR(500),

    CONSTRAINT fk_worktime_project_worktime
      FOREIGN KEY (worktime_id) REFERENCES worktime(id)
      ON DELETE CASCADE,
    CONSTRAINT fk_project_worktime
      FOREIGN KEY (project_id) REFERENCES project(id)
      ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_worktime_running ON worktime (end_time);
CREATE INDEX idx_project_worktime_by_day ON project_worktime (worktime_id, end_time);
