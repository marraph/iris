package com.marraph.iris.controller;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.Team;
import com.marraph.iris.service.plain.organisation.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/set/organisation/{id}:{organisationId}")
    public CompletableFuture<ResponseEntity<Team>> setOrganisation(@PathVariable Long id, @PathVariable Long organisationId) {
        return teamService.addToOrganisation(id, organisationId).thenApply(team -> {
            if (team.isEmpty()) throw new EntryNotFoundException();
            else return ResponseEntity.ok(team.get());
        });
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/add/project/{id}:{projectId}")
    public CompletableFuture<ResponseEntity<Team>> addProject(@PathVariable Long id, @PathVariable Long projectId) {
        return teamService.addProject(id, projectId).thenApply(team -> {
            if (team.isEmpty()) throw new EntryNotFoundException();
            else return ResponseEntity.ok(team.get());
        });
    }

}