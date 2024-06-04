package com.marraph.iris.model.organisation;

import com.marraph.iris.model.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "teams")
public final class Team extends Auditable {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Organisation organisation;

    @OneToMany
    @JoinTable(
            name = "team_projects",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects;
}