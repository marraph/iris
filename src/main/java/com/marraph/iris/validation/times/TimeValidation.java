package com.marraph.iris.validation.times;

import com.marraph.iris.model.time.TimeEntry;
import lombok.experimental.UtilityClass;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TimeValidation {

    public boolean hasTime(List<TimeEntry> times, Date startDate, Date endDate) {
        final var consideredTimes = eliminateOutOfScope(times, startDate, endDate);
        return consideredTimes.isEmpty();
    }

    public List<TimeEntry> eliminateOutOfScope(List<TimeEntry> times, Date startDate, Date endDate) {
        return times.stream()
                .filter(entry -> !(entry.getStartDate().before(startDate) && entry.getEndDate().before(startDate)))
                .filter(entry -> !(entry.getEndDate().after(endDate) && entry.getStartDate().after(endDate)))
                .collect(Collectors.toList());
    }

    public List<TimeEntry> eliminateOverlapping(List<TimeEntry> times, Date startDate, Date endDate) {
        return times.stream()
                .filter(entry -> !(entry.getEndDate().after(startDate) && entry.getEndDate().before(endDate)))
                .filter(entry -> !(entry.getStartDate().after(startDate) && entry.getStartDate().before(endDate)))
                .collect(Collectors.toList());
    }

}
