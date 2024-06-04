package com.marraph.iris.controller;

import com.marraph.iris.model.task.Task;
import com.marraph.iris.service.plain.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1/task")
@RestController
public final class TaskController extends AbstractController<Task> {

    @Autowired
    TaskController(TaskService taskService) {
        super(taskService);
    }
}
