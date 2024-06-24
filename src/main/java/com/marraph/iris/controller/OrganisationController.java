package com.marraph.iris.controller;

import com.marraph.iris.model.organisation.Organisation;
import com.marraph.iris.service.plain.organisation.OrganisationService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/organisation")
@RestController
public final class OrganisationController extends AbstractController<Organisation> {

    private final OrganisationService organisationService;

    @Autowired
    OrganisationController(OrganisationService organisationService) {
        super(organisationService);

        this.organisationService = organisationService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Organisation>> createEntity(@RequestBody Organisation entity) {
        return this.organisationService.create(entity).thenApply(ResponseEntity::ok);
    }
}