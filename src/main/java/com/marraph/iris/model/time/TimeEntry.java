package com.marraph.iris.model.time;

import com.marraph.iris.model.Auditable;
import com.marraph.iris.model.organisation.Project;
import com.marraph.iris.model.task.Task;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "TimeEntries")
public final class TimeEntry extends Auditable {

    @ManyToOne
    private DailyEntry dailyEntry;

    @ManyToOne
    private Task task;

    @ManyToOne
    private Project project;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    private String comment;

}