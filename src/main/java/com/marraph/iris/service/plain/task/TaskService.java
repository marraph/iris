package com.marraph.iris.service.plain.task;

import com.marraph.iris.model.task.Task;
import com.marraph.iris.service.plain.AbstractService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface TaskService extends AbstractService<Task> {

    @Async
    CompletableFuture<Task> create(Task entity, Long projectId);

}