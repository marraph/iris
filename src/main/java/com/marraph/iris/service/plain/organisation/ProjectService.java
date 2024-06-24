package com.marraph.iris.service.plain.organisation;

import com.marraph.iris.model.organisation.Project;
import com.marraph.iris.service.plain.AbstractService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public interface ProjectService extends AbstractService<Project> {

    @Async
    CompletableFuture<Project> create(Project entity, Long teamId);

}