package com.marraph.iris.service.plain.organisation;

import com.marraph.iris.model.organisation.User;
import com.marraph.iris.service.plain.AbstractService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface UserService extends AbstractService<User> {

    @Async
    CompletableFuture<User> create(User entity);

    @Async
    CompletableFuture<Boolean> emailInUse(User entity);

    @Async
    CompletableFuture<User> addToTeam(Long id, Long teamId);

}