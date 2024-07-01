package com.marraph.iris.controller;

import com.marraph.iris.model.time.TimeEntry;
import com.marraph.iris.service.plain.time.TimeEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/timeentry")
@RestController
public final class TimeEntryController extends AbstractController<TimeEntry> {

    private final TimeEntryService timeEntryService;

    @Autowired
    TimeEntryController(TimeEntryService timeEntryService) {
        super(timeEntryService);

        this.timeEntryService = timeEntryService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<TimeEntry>> createEntity(@RequestBody TimeEntry entity) {
        return this.timeEntryService.create(entity).thenApply(ResponseEntity::ok);
    }
}
