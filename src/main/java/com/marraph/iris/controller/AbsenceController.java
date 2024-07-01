package com.marraph.iris.controller;

import com.marraph.iris.model.time.Absence;
import com.marraph.iris.service.plain.time.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/absence")
@RestController
public final class AbsenceController extends AbstractController<Absence> {

    private final AbsenceService absenceService;

    @Autowired
    AbsenceController(AbsenceService absenceService) {
        super(absenceService);

        this.absenceService = absenceService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Absence>> createEntity(@RequestBody Absence absence) {
        return this.absenceService.create(absence).thenApply(ResponseEntity::ok);
    }
}
