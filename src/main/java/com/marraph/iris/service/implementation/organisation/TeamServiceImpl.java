package com.marraph.iris.service.implementation.organisation;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.Project;
import com.marraph.iris.model.organisation.Team;
import com.marraph.iris.model.task.Topic;
import com.marraph.iris.repository.OrganisationRepository;
import com.marraph.iris.repository.ProjectRepository;
import com.marraph.iris.repository.TeamRepository;
import com.marraph.iris.repository.TopicRepository;
import com.marraph.iris.service.plain.organisation.TeamService;
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
public final class TeamServiceImpl implements TeamService {

    private final OrganisationRepository organisationRepository;
    private final ProjectRepository projectRepository;
    private final TopicRepository topicRepository;
    private final TeamRepository teamRepository;
    private final ExampleMatcher modelMatcher;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, OrganisationRepository organisationRepository, ProjectRepository projectRepository, TopicRepository topicRepository) {
        this.organisationRepository = organisationRepository;
        this.projectRepository = projectRepository;
        this.topicRepository = topicRepository;
        this.teamRepository = teamRepository;

        this.modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("name", ignoreCase());
    }

    @Override
    public CompletableFuture<Team> create(Team entity) {
        return this.exists(entity).thenCompose(exists -> {

            if (exists) {
                final var found = teamRepository.findOne(Example.of(entity, modelMatcher));
                if (found.isPresent()) return CompletableFuture.completedFuture(found.get());
            }

            entity.setCreatedDate(LocalDateTime.now());
            entity.setLastModifiedDate(LocalDateTime.now());
            return CompletableFuture.completedFuture(teamRepository.save(entity));
        });
    }

    @Override
    public CompletableFuture<Team> update(Long id, Team updatedEntity) {
        CompletableFuture<Team> future = new CompletableFuture<>();
        final var entry = teamRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setName(updatedEntity.getName());
        entry.setLastModifiedDate(LocalDateTime.now());
        teamRepository.save(entry);

        future.complete(entry);
        return future;
    }

    @Override
    public CompletableFuture<Optional<Team>> getById(Long id) {
        return CompletableFuture.completedFuture(teamRepository.findById(id).or(() -> {
            throw new EntryNotFoundException(id);
        }));
    }

    @Override
    public CompletableFuture<List<Team>> getAll() {
        return CompletableFuture.completedFuture(teamRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public CompletableFuture<Boolean> exists(Team entity) {
        return CompletableFuture.completedFuture(teamRepository.exists(Example.of(entity, modelMatcher)));
    }

    @Override
    public CompletableFuture<Optional<Team>> addToOrganisation(Long id, Long organisationId) {
        final var organisation = this.organisationRepository.findById(organisationId);
        if (organisation.isEmpty()) return CompletableFuture.completedFuture(Optional.empty());

        final var entry = teamRepository.findById(id);
        if (entry.isEmpty()) return CompletableFuture.completedFuture(Optional.empty());

        entry.get().setOrganisation(organisation.get());
        final var updatedTeam = teamRepository.save(entry.get());
        return CompletableFuture.completedFuture(Optional.of(updatedTeam));
    }

    @Override
    public CompletableFuture<Optional<Team>> addProject(Long id, Long projectId) {
        final var project = this.projectRepository.findById(projectId);
        if (project.isEmpty()) return CompletableFuture.completedFuture(Optional.empty());

        final var entry = teamRepository.findById(id);
        if (entry.isEmpty()) return CompletableFuture.completedFuture(Optional.empty());

        entry.get().getProjects().add(project.get());
        final var updatedTeam = teamRepository.save(entry.get());
        return CompletableFuture.completedFuture(Optional.of(updatedTeam));
    }

    @Override
    public CompletableFuture<Optional<Team>> addTopic(Long id, Long topicId) {
        final var topic = this.topicRepository.findById(topicId);
        if (topic.isEmpty()) return CompletableFuture.completedFuture(Optional.empty());

        final var entry = teamRepository.findById(id);
        if (entry.isEmpty()) return CompletableFuture.completedFuture(Optional.empty());

        entry.get().getTopics().add(topic.get());
        final var updatedTeam = teamRepository.save(entry.get());
        return CompletableFuture.completedFuture(Optional.of(updatedTeam));
    }

    @Override
    public CompletableFuture<List<Project>> getProjects(Long id) {
        final var optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isEmpty()) throw new EntryNotFoundException(id);
        return CompletableFuture.completedFuture(optionalTeam.get().getProjects().stream().toList());
    }

    @Override
    public CompletableFuture<List<Topic>> getTopics(Long id) {
        final var optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isEmpty()) throw new EntryNotFoundException(id);
        return CompletableFuture.completedFuture(optionalTeam.get().getTopics().stream().toList());

    }
}
