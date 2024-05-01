package com.marraph.iris.controller;

import com.marraph.iris.model.task.Task;
import com.marraph.iris.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/task")
@RestController
public final class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Task>> createTask(@RequestBody Task task) {
        return taskService.create(task).thenApply(ResponseEntity::ok);
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<ResponseEntity<Task>> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.update(id, task).thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Task>> getTaskById(@PathVariable Long id) {
        return taskService.getById(id)
                .thenApply(opt -> opt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deleteTask(@PathVariable Long id) {
        return taskService.delete(id).thenApply(deleted -> deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build());
    }

    @GetMapping("/exists")
    public CompletableFuture<ResponseEntity<Boolean>> taskExists(Task task) {
        return taskService.exists(task).thenApply(ResponseEntity::ok);
    }
}
