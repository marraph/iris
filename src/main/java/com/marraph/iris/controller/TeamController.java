package com.marraph.iris.controller;

import com.marraph.iris.model.organisation.Team;
import com.marraph.iris.service.organisation.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/team")
@RestController
public final class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Team>> createTeam(@RequestBody Team team) {
        return teamService.create(team).thenApply(ResponseEntity::ok);
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<ResponseEntity<Team>> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return teamService.update(id, team).thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Team>> getTeamById(@PathVariable Long id) {
        return teamService.getById(id)
                .thenApply(opt -> opt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deleteTeam(@PathVariable Long id) {
        return teamService.delete(id).thenApply(deleted -> deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build());
    }

    @GetMapping("/exists")
    public CompletableFuture<ResponseEntity<Boolean>> teamExists(Team team) {
        return teamService.exists(team).thenApply(ResponseEntity::ok);
    }
}
