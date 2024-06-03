package com.marraph.iris.dto.task;

import com.marraph.iris.model.data.Priority;
import com.marraph.iris.model.data.Status;

import java.util.Date;

public record TaskDTO(
        Long id,
        String name,
        String description,
        TopicDTO topic,
        Boolean isArchived,
        Date duration,
        Date deadline,
        Status status,
        Priority priority
) {
}