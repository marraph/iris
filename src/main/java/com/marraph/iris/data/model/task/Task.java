package com.marraph.iris.data.model.task;

import com.marraph.iris.data.model.Auditable;
import com.marraph.iris.data.model.data.Priority;
import com.marraph.iris.data.model.data.Status;
import com.marraph.iris.data.model.organisation.Project;
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
    private Project project;

    @OneToOne
    private Topic topic;

    @Column(nullable = false, unique = true)
    private Boolean isArchived;

    @Column(nullable = false, unique = true)
    private Date duration;

    @Column(nullable = false, unique = true)
    private Date deadline;

    @Column(nullable = false, unique = true)
    private Status status;

    @Column(nullable = false, unique = true)
    private Priority priority;
}