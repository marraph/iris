package com.marraph.iris.model.organisation;

import com.marraph.iris.model.Auditable;
import com.marraph.iris.model.data.Priority;
import com.marraph.iris.model.task.Task;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "projects")
public final class Project extends Auditable {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Priority priority;

    @Column(nullable = false)
    private Boolean isArchived;

    @OneToMany
    @JoinTable(
            name = "project_tasks",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private Set<Task> tasks;

}