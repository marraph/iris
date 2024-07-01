package com.marraph.iris.service.implementation.organisation;

import com.marraph.iris.exception.ConnectEntryException;
import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.Project;
import com.marraph.iris.repository.ProjectRepository;
import com.marraph.iris.repository.TeamRepository;
import com.marraph.iris.service.implementation.AbstractServiceImpl;
import com.marraph.iris.service.plain.organisation.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public final class ProjectServiceImpl extends AbstractServiceImpl<Project> implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, TeamRepository teamRepository) {
        super(projectRepository, ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("name", ignoreCase())
        );

        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
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
    public CompletableFuture<Project> create(Project entity, Long teamId) {
        final var result = this.create(entity);

        result.thenAccept(project -> addProject(entity, teamId));
        return result;
    }

    private CompletableFuture<Project> create(Project entity) {
        return this.exists(entity).thenCompose(exists -> {

            if (exists) {
                final var found = projectRepository.findOne(Example.of(entity, getExampleMatcher()));
                if (found.isPresent()) return CompletableFuture.completedFuture(entity);
            }

            entity.setCreatedDate(LocalDateTime.now());
            entity.setLastModifiedDate(LocalDateTime.now());
            return CompletableFuture.completedFuture(projectRepository.save(entity));
        });
    }

    public void addProject(Project project, Long teamId) {
        final var entry = teamRepository.findById(teamId).orElseThrow(() -> new ConnectEntryException("Team not found"));
        entry.getProjects().add(project);
        teamRepository.save(entry);
    }

}