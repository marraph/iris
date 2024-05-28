package com.marraph.iris.service.organisation;

import com.marraph.iris.model.organisation.User;
import com.marraph.iris.service.AbstractService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface UserService extends AbstractService<User> {


    @Async
    CompletableFuture<Boolean> emailInUse(User entity);

}