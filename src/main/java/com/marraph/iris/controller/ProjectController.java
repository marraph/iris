package com.marraph.iris.controller;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.Project;
import com.marraph.iris.model.wrapper.ProjectCreation;
import com.marraph.iris.service.plain.organisation.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/project")
@RestController
public final class ProjectController extends AbstractController<Project> {

    private final ProjectService projectService;

    @Autowired
    ProjectController(ProjectService projectService) {
        super(projectService);

        this.projectService = projectService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Project>> createEntity(@RequestBody ProjectCreation entity) {
        return this.projectService.create(entity.project(), entity.teamId()).thenApply(ResponseEntity::ok);
    }
}