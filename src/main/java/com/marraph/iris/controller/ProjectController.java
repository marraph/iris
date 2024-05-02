package com.marraph.iris.controller;

import com.marraph.iris.model.organisation.Project;
import com.marraph.iris.service.organisation.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1/project")
@RestController
public final class ProjectController extends AbstractController<Project> {

    @Autowired
    ProjectController(ProjectService service) {
        super(service);
    }
}