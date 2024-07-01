package com.marraph.iris.model.time;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marraph.iris.model.Auditable;
import com.marraph.iris.model.organisation.Project;
import com.marraph.iris.model.task.Task;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TimeEntries")
public final class TimeEntry extends Auditable {

    @ManyToOne
    private Task task;

    @ManyToOne
    private Project project;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime startDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime endDate;

    private String comment;

}