package com.marraph.iris.data.dto.task;

import com.marraph.iris.data.model.data.Priority;
import com.marraph.iris.data.model.data.Status;

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