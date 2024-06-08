package com.marraph.iris.service.plain.organisation;

import com.marraph.iris.model.organisation.User;
import com.marraph.iris.service.plain.AbstractService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public interface UserService extends AbstractService<User> {

    @Async
    CompletableFuture<Boolean> emailInUse(User entity);

    @Async
    CompletableFuture<Optional<User>> addToTeam(Long id, Long teamId);

}