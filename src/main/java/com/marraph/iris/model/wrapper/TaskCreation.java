package com.marraph.iris.model.wrapper;

import com.marraph.iris.model.task.Task;

public record TaskCreation(Task task, Long projectId) {
}
