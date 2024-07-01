package com.marraph.iris.service.plain.time;

import com.marraph.iris.model.time.Absence;
import com.marraph.iris.service.plain.AbstractService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface AbsenceService extends AbstractService<Absence> {

    @Async
    CompletableFuture<Absence> create(Absence absence);

}
