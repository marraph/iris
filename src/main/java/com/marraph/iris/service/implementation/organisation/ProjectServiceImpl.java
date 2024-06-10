package com.marraph.iris.service.implementation.organisation;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.Project;
import com.marraph.iris.repository.ProjectRepository;
import com.marraph.iris.repository.TaskRepository;
import com.marraph.iris.service.plain.organisation.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public final class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ExampleMatcher modelMatcher;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;

        this.modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("name", ignoreCase());
    }

    @Override
    public CompletableFuture<Project> create(Project entity) {
        return this.exists(entity).thenCompose(exists -> {

            if (exists) {
                final var found = projectRepository.findOne(Example.of(entity, modelMatcher));
                if (found.isPresent()) return CompletableFuture.completedFuture(entity);
            }

            entity.setCreatedDate(LocalDateTime.now());
            entity.setLastModifiedDate(LocalDateTime.now());
            return CompletableFuture.completedFuture(projectRepository.save(entity));
        });
    }

    @Override
    public CompletableFuture<Project> update(Project updatedEntity) {
        CompletableFuture<Project> future = new CompletableFuture<>();
        final var id = updatedEntity.getId();

        if (id == null) throw new IllegalArgumentException("ID is null");
        final var entry = projectRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setName(updatedEntity.getName());
        entry.setLastModifiedDate(LocalDateTime.now());
        projectRepository.save(entry);

        future.complete(entry);
        return future;
    }

    @Override
    public CompletableFuture<Optional<Project>> getById(Long id) {
        return CompletableFuture.completedFuture(projectRepository.findById(id).or(() -> {
            throw new EntryNotFoundException(id);
        }));
    }

    @Override
    public CompletableFuture<List<Project>> getAll() {
        return CompletableFuture.completedFuture(projectRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public CompletableFuture<Boolean> exists(Project entity) {
        return CompletableFuture.completedFuture(projectRepository.exists(Example.of(entity, modelMatcher)));
    }

    @Override
    public CompletableFuture<Optional<Project>> addTask(Long id, Long taskId) {
        final var task = this.taskRepository.findById(taskId);
        if (task.isEmpty()) return CompletableFuture.completedFuture(Optional.empty());

        final var entry = projectRepository.findById(id);
        if (entry.isEmpty()) return CompletableFuture.completedFuture(Optional.empty());

        entry.get().getTasks().add(task.get());
        final var updatedProject = projectRepository.save(entry.get());
        return CompletableFuture.completedFuture(Optional.of(updatedProject));
    }
}