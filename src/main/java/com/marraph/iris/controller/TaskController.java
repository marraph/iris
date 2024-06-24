package com.marraph.iris.controller;

import com.marraph.iris.model.task.Task;
import com.marraph.iris.model.wrapper.TaskCreation;
import com.marraph.iris.service.plain.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/task")
@RestController
public final class TaskController extends AbstractController<Task> {

    private final TaskService taskService;

    @Autowired
    TaskController(TaskService taskService) {
        super(taskService);

        this.taskService = taskService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Task>> createEntity(@RequestBody TaskCreation entity) {
        return this.taskService.create(entity.task(), entity.projectId()).thenApply(ResponseEntity::ok);
    }
}
