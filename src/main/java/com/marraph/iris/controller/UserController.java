package com.marraph.iris.controller;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.Team;
import com.marraph.iris.model.organisation.User;
import com.marraph.iris.service.plain.organisation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/user")
@RestController
public final class UserController extends AbstractController<User> {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        super(userService);

        this.userService = userService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<User>> createEntity(@RequestBody User entity) {
        return this.userService.create(entity).thenApply(ResponseEntity::ok);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/add/{id}:{teamId}")
    public CompletableFuture<ResponseEntity<User>> addToTeam(@PathVariable Long id, @PathVariable Long teamId) {
        return userService.addToTeam(id, teamId).thenApply(user -> {
            if (user.isEmpty()) throw new EntryNotFoundException();
            else return ResponseEntity.ok(user.get());
        });
    }
}