package com.marraph.iris.service.plain.task;

import com.marraph.iris.model.task.Topic;
import com.marraph.iris.service.plain.AbstractService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface TopicService extends AbstractService<Topic> {

    @Async
    CompletableFuture<Topic> create(Topic entity, Long teamId);

}