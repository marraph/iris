package com.marraph.iris.model.time;

import com.marraph.iris.model.Auditable;
import com.marraph.iris.model.data.AbsenceType;
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

    Date startDate;
    Date endDate;
    AbsenceType absenceType;
    String comment;

}