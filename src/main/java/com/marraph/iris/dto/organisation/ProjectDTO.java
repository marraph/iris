package com.marraph.iris.dto.organisation;

import com.marraph.iris.dto.task.TaskDTO;
import com.marraph.iris.model.data.Priority;

import java.util.Set;

public record ProjectDTO(
        Long id,
        String name,
        String description,
        Priority priority,
        Boolean isArchived,
        Set<TaskDTO> tasks
) {
}