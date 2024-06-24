package com.marraph.iris.service.plain.organisation;

import com.marraph.iris.model.organisation.Project;
import com.marraph.iris.model.organisation.Team;
import com.marraph.iris.model.task.Topic;
import com.marraph.iris.service.plain.AbstractService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface TeamService extends AbstractService<Team> {

    @Async
    CompletableFuture<Team> create(Team entity, Long organisationId);

    @Async
    CompletableFuture<List<Project>> getProjects(Long id);

    @Async
    CompletableFuture<List<Topic>> getTopics(Long id);

}