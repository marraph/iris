package com.marraph.iris.service.plain.organisation;

import com.marraph.iris.model.organisation.Organisation;
import com.marraph.iris.model.task.Topic;
import com.marraph.iris.service.plain.AbstractService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface OrganisationService extends AbstractService<Organisation> {

    @Async
    CompletableFuture<Organisation> create(Organisation entity);

}