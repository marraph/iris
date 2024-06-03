package com.marraph.iris.data.dto.organisation;

import com.marraph.iris.data.dto.task.TaskDTO;
import com.marraph.iris.data.model.data.Priority;

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