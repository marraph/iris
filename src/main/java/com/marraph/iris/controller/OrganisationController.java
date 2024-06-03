package com.marraph.iris.controller;

import com.marraph.iris.data.model.organisation.Organisation;
import com.marraph.iris.service.plain.organisation.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1/organisation")
@RestController
public final class OrganisationController extends AbstractController<Organisation> {

    @Autowired
    OrganisationController(OrganisationService organisationService) {
        super(organisationService);
    }
}