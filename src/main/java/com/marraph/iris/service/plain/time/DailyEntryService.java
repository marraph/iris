package com.marraph.iris.service.plain.time;

import com.marraph.iris.model.time.DailyEntry;
import com.marraph.iris.service.plain.AbstractService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface DailyEntryService extends AbstractService<DailyEntry> {

    @Async
    CompletableFuture<DailyEntry> create(DailyEntry dailyEntry);

}
