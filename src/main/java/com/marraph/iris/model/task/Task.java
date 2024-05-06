package com.marraph.iris.model.task;

import com.marraph.iris.model.Auditable;
import com.marraph.iris.model.data.Priority;
import com.marraph.iris.model.data.Status;
import com.marraph.iris.model.organisation.Project;
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

    @OneToOne
    @Column(nullable = false)
    private Project project;

    @OneToOne
    @Column(nullable = false)
    private Topic topic;

    @Column(nullable = false)
    private Boolean isArchived;

    @Column(nullable = false)
    private Date duration;

    @Column(nullable = false)
    private Date deadline;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Priority priority;
}