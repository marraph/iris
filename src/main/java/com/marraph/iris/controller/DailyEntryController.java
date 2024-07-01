package com.marraph.iris.controller;

import com.marraph.iris.model.time.DailyEntry;
import com.marraph.iris.service.plain.time.DailyEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/dailyentry")
@RestController
public final class DailyEntryController extends AbstractController<DailyEntry> {

    private final DailyEntryService dailyEntryService;

    @Autowired
    DailyEntryController(DailyEntryService dailyEntryService) {
        super(dailyEntryService);

        this.dailyEntryService = dailyEntryService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<DailyEntry>> createEntity(@RequestBody DailyEntry entity) {
        return this.dailyEntryService.create(entity).thenApply(ResponseEntity::ok);
    }
}
