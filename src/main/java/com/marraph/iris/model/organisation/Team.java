package com.marraph.iris.model.organisation;

import com.marraph.iris.model.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "teams")
public final class Team extends Auditable {

    @Column(nullable = false)
    private String name;

    @OneToOne
    @Column(nullable = false)
    private Organisation description;

}