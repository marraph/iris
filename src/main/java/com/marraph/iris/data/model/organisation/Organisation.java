package com.marraph.iris.data.model.organisation;

import com.marraph.iris.data.model.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "organisations")
public final class Organisation extends Auditable {

    @Column(nullable = false)
    private String name;

}