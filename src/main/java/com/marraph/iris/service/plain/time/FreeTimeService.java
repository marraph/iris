package com.marraph.iris.service.plain.time;

import com.marraph.iris.data.model.time.TimeEntry;
import com.marraph.iris.service.validation.times.data.FreeTime;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface FreeTimeService {

    @Async
    CompletableFuture<Boolean> hasTime(List<TimeEntry> times);

    @Async
    CompletableFuture<FreeTime> getNextSpace(List<TimeEntry> times, FreeTime freeTime);

    @Async
    CompletableFuture<List<FreeTime>> getSpaces(List<TimeEntry> times);

}