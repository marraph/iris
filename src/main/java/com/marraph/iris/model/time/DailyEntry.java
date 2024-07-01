package com.marraph.iris.model.time;

import com.marraph.iris.model.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "DailyEntries")
public final class DailyEntry extends Auditable {

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    private String comment;

    @OneToMany
    @JoinTable(
            name = "daily_time_entries",
            joinColumns = @JoinColumn(name = "dailyentry_id"),
            inverseJoinColumns = @JoinColumn(name = "timeentry_id")
    )
    private Set<TimeEntry> timeEntries;

}