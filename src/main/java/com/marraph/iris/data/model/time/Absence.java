package com.marraph.iris.data.model.time;

import com.marraph.iris.data.model.Auditable;
import com.marraph.iris.data.model.data.AbsenceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Absences")
public final class Absence extends Auditable {

    @Column(nullable = false)
    Date startDate;

    @Column(nullable = false)
    Date endDate;

    @Column(nullable = false)
    AbsenceType absenceType;

    String comment;

}