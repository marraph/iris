package com.marraph.iris.controller;

import com.marraph.iris.model.organisation.Project;
import com.marraph.iris.service.organisation.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/project")
@RestController
public final class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Project>> createProject(@RequestBody Project project) {
        return projectService.create(project).thenApply(ResponseEntity::ok);
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<ResponseEntity<Project>> updateProject(@PathVariable Long id, @RequestBody Project project) {
        return projectService.update(id, project).thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Project>> getProjectById(@PathVariable Long id) {
        return projectService.getById(id)
                .thenApply(opt -> opt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deleteProject(@PathVariable Long id) {
        return projectService.delete(id).thenApply(deleted -> deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build());
    }

    @GetMapping("/exists")
    public CompletableFuture<ResponseEntity<Boolean>> projectExists(Project project) {
        return projectService.exists(project).thenApply(ResponseEntity::ok);
    }
}
