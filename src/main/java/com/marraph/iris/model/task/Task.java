package com.marraph.iris.model.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marraph.iris.model.Auditable;
import com.marraph.iris.model.data.Priority;
import com.marraph.iris.model.data.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public final class Task extends Auditable {

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    private Topic topic;

    @Column(nullable = false)
    private Boolean isArchived;

    private Float duration;

    private Float bookedDuration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime deadline;

    private Status status;

    private Priority priority;
}