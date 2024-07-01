package com.marraph.iris.service.implementation.task;

import com.marraph.iris.exception.ConnectEntryException;
import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.task.Task;
import com.marraph.iris.repository.ProjectRepository;
import com.marraph.iris.repository.TaskRepository;
import com.marraph.iris.service.implementation.AbstractServiceImpl;
import com.marraph.iris.service.plain.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public final class TaskServiceImpl extends AbstractServiceImpl<Task> implements TaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository) {
        super(taskRepository, ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("topics")
                .withIgnorePaths("topic_id")
                .withMatcher("name", ignoreCase())
                .withMatcher("description", ignoreCase())
        );

        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public CompletableFuture<Task> update(Task updatedEntity) {
        CompletableFuture<Task> future = new CompletableFuture<>();
        final var id = updatedEntity.getId();
        if (id == null) throw new IllegalArgumentException("ID is null");
        final var entry = taskRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setName(updatedEntity.getName());
        entry.setDescription(updatedEntity.getDescription());
        entry.setDuration(updatedEntity.getDuration());
        entry.setDeadline(updatedEntity.getDeadline());
        entry.setIsArchived(updatedEntity.getIsArchived());
        entry.setTopic(updatedEntity.getTopic());
        entry.setLastModifiedDate(LocalDateTime.now());
        taskRepository.save(entry);

        future.complete(entry);
        return future;
    }

    @Override
    public CompletableFuture<Task> create(Task entity, Long projectId) {
        final var result = this.create(entity);
        result.thenAccept(task -> addToProject(task, projectId));
        return result;
    }

    private void addToProject(Task task, Long projectId) {
        final var project = this.projectRepository.findById(projectId).orElseThrow(() -> new ConnectEntryException("Can't find team with id: " + projectId));
        project.getTasks().add(task);
        this.projectRepository.save(project);
    }

    private CompletableFuture<Task> create(Task entity) {
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastModifiedDate(LocalDateTime.now());
        return CompletableFuture.completedFuture(taskRepository.save(entity));
    }
}
