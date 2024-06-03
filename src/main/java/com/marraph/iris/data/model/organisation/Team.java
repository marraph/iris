package com.marraph.iris.data.model.organisation;

import com.marraph.iris.data.model.Auditable;
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

    @OneToOne
    private Organisation organisation;

    @ManyToMany(mappedBy = "teams")
    private Set<User> member;

}