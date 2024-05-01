package com.marraph.iris.model.organisation;

import com.marraph.iris.model.Auditable;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "organisations")
public final class Organisation extends Auditable {

    @Column(nullable = false)
    private String name;

}