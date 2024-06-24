package com.marraph.iris.controller;

import com.marraph.iris.model.organisation.Project;
import com.marraph.iris.model.organisation.Team;
import com.marraph.iris.model.task.Topic;
import com.marraph.iris.model.wrapper.TeamCreation;
import com.marraph.iris.service.plain.organisation.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/team")
@RestController
public final class TeamController extends AbstractController<Team> {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
       super(teamService);

        this.teamService = teamService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Team>> createEntity(@RequestBody TeamCreation entity) {
        return this.teamService.create(entity.team(), entity.organisationId()).thenApply(ResponseEntity::ok);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get/projects({id}")
    public CompletableFuture<ResponseEntity<List<Project>>> getProjects(@PathVariable Long id) {
        return teamService.getProjects(id).thenApply(ResponseEntity::ok);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get/topic({id}")
    public CompletableFuture<ResponseEntity<List<Topic>>> getTopics(@PathVariable Long id) {
        return teamService.getTopics(id).thenApply(ResponseEntity::ok);
    }
}