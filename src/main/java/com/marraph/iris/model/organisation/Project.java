package com.marraph.iris.model.organisation;

import com.marraph.iris.model.Auditable;
import com.marraph.iris.model.data.Priority;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "projects")
public final class Project extends Auditable {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToOne
    @Column(nullable = false)
    private Team team;

    @Column(nullable = false)
    private Priority priority;

    @Column(nullable = false)
    private Boolean isArchived;

}