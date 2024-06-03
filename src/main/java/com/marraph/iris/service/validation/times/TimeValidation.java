package com.marraph.iris.service.validation.times;

import com.marraph.iris.data.model.time.TimeEntry;
import com.marraph.iris.service.validation.times.data.FreeTimeTypes;
import com.marraph.iris.service.validation.times.data.FreeTime;
import lombok.experimental.UtilityClass;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    public List<FreeTime> findPossiblePeriods(List<FreeTime> times, FreeTimeTypes freeTimeTypes) {
        final var results = new ArrayList<FreeTime>();

        for (final var time : times) {
            if (freeTimeTypes.getValue() > getFreeTimeMinutes(time)) continue;
            results.add(time);
        }

        return results;
    }

    private List<FreeTime> getPossibleFreeTimes(List<TimeEntry> entries) {
        final var options = getPossibleCombinations(entries);
        final var optionalResults = options.stream().map(TimeValidation::getDifference);
        final var possibleResults = new ArrayList<FreeTime>();

        for (final var result : optionalResults.toList()) {
            if (result.isEmpty()) continue;
            possibleResults.add(result.get());
        }

        final var results = new ArrayList<FreeTime>();

        for (final var possibleResult : possibleResults) {
            if (!hasTime(entries, possibleResult.startDate(), possibleResult.endDate())) continue;
            results.add(possibleResult);
        }

        return results;
    }

    private int getFreeTimeMinutes(FreeTime freeTime) {
        return freeTime.endDate().getMinutes() - freeTime.startDate().getMinutes();
    }

    private List<Pair<TimeEntry, TimeEntry>> getPossibleCombinations(List<TimeEntry> items) {
        final var pairs = new ArrayList<Pair<TimeEntry, TimeEntry>>();

        for (int i = 0; i < items.size(); i++) {
            for (int j = i + 1; j < items.size(); j++) {
                final var pair = new Pair<>(items.get(i), items.get(j));
                pairs.add(pair);
            }
        }

        return pairs;
    }

    private Optional<FreeTime> getDifference(Pair<TimeEntry, TimeEntry> pair) {
        final var entryOne = pair.a;
        final var entryTwo = pair.b;

        if (entryOne.getStartDate().before(entryTwo.getStartDate()) && entryOne.getEndDate().after(entryTwo.getEndDate())) {
            return Optional.of(new FreeTime(entryOne.getEndDate(), entryTwo.getStartDate()));
        }

        if (entryTwo.getStartDate().before(entryOne.getStartDate()) && entryTwo.getEndDate().after(entryOne.getEndDate())) {
            return Optional.of(new FreeTime(entryOne.getEndDate(), entryTwo.getStartDate()));
        }

        return Optional.empty();
    }

}