package com.marraph.iris.model.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marraph.iris.model.Auditable;
import com.marraph.iris.model.data.Priority;
import com.marraph.iris.model.data.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    @Column(nullable = false)
    private Date duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    @Column(nullable = false)
    private Date deadline;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Priority priority;
}