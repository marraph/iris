package com.marraph.iris.service.implementation.task;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.data.model.task.Task;
import com.marraph.iris.repository.TaskRepository;
import com.marraph.iris.service.plain.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public final class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ExampleMatcher modelMatcher;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

        this.modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("name", ignoreCase())
                .withMatcher("description", ignoreCase());
    }

    @Override
    public CompletableFuture<Task> create(Task entity) {
        return this.exists(entity).thenCompose(exists -> {

            if (exists) {
                final var found = taskRepository.findOne(Example.of(entity, modelMatcher));
                if (found.isPresent()) return CompletableFuture.completedFuture(found.get());
            }

            return CompletableFuture.completedFuture(taskRepository.save(entity));
        });
    }

    @Override
    public CompletableFuture<Task> update(Long id, Task updatedEntity) {
        CompletableFuture<Task> future = new CompletableFuture<>();
        final var entry = taskRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setName(updatedEntity.getName());
        entry.setDescription(updatedEntity.getDescription());
        entry.setDuration(updatedEntity.getDuration());
        entry.setDeadline(updatedEntity.getDeadline());
        entry.setIsArchived(updatedEntity.getIsArchived());
        entry.setTopic(updatedEntity.getTopic());
        taskRepository.save(entry);

        future.complete(entry);
        return future;
    }

    @Override
    public CompletableFuture<Optional<Task>> getById(Long id) {
        return CompletableFuture.completedFuture(taskRepository.findById(id).or(() -> {
            throw new EntryNotFoundException(id);
        }));
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public CompletableFuture<Boolean> exists(Task entity) {
        return CompletableFuture.completedFuture(taskRepository.exists(Example.of(entity, modelMatcher)));
    }
}
