package com.marraph.iris.service.plain.organisation;

import com.marraph.iris.model.organisation.Team;
import com.marraph.iris.service.plain.AbstractService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public interface TeamService extends AbstractService<Team> {

    @Async
    CompletableFuture<Optional<Team>> addToOrganisation(Long id, Long organisationId);

}