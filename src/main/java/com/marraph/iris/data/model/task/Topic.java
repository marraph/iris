package com.marraph.iris.data.model.task;

import com.marraph.iris.data.model.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "topics")
public final class Topic extends Auditable {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String hexCode;
}