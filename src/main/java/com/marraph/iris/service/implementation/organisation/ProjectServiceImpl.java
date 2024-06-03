package com.marraph.iris.service.implementation.organisation;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.data.model.organisation.Project;
import com.marraph.iris.repository.ProjectRepository;
import com.marraph.iris.service.plain.organisation.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public final class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ExampleMatcher modelMatcher;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;

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

            return CompletableFuture.completedFuture(projectRepository.save(entity));
        });
    }

    @Override
    public CompletableFuture<Project> update(Long id, Project updatedEntity) {
        CompletableFuture<Project> future = new CompletableFuture<>();
        final var entry = projectRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setName(updatedEntity.getName());
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
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public CompletableFuture<Boolean> exists(Project entity) {
        return CompletableFuture.completedFuture(projectRepository.exists(Example.of(entity, modelMatcher)));
    }
}