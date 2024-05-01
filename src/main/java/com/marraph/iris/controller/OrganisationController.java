package com.marraph.iris.controller;

import com.marraph.iris.model.organisation.Organisation;
import com.marraph.iris.service.organisation.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/organisation")
@RestController
public final class OrganisationController {

    private final OrganisationService organisationService;

    @Autowired
    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Organisation>> createOrganisation(Organisation organisation) {
        return organisationService.create(organisation).thenApply(ResponseEntity::ok);
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<ResponseEntity<Organisation>> updateOrganisation(@PathVariable Long id, Organisation organisation) {
        return organisationService.update(id, organisation).thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Organisation>> getOrganisationById(@PathVariable Long id) {
        return organisationService.getById(id)
                .thenApply(opt -> opt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deleteOrganisation(@PathVariable Long id) {
        return organisationService.delete(id).thenApply(deleted -> deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build());
    }

    @GetMapping("/exists")
    public CompletableFuture<ResponseEntity<Boolean>> organisationExists(Organisation organisation) {
        return organisationService.exists(organisation).thenApply(ResponseEntity::ok);
    }
}