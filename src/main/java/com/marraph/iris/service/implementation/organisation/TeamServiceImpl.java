package com.marraph.iris.service.implementation.organisation;

import com.marraph.iris.exception.ConnectEntryException;
import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.Project;
import com.marraph.iris.model.organisation.Team;
import com.marraph.iris.model.task.Topic;
import com.marraph.iris.repository.OrganisationRepository;
import com.marraph.iris.repository.ProjectRepository;
import com.marraph.iris.repository.TeamRepository;
import com.marraph.iris.repository.TopicRepository;
import com.marraph.iris.service.implementation.AbstractServiceImpl;
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
public final class TeamServiceImpl extends AbstractServiceImpl<Team> implements TeamService {

    private final OrganisationRepository organisationRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, OrganisationRepository organisationRepository, ProjectRepository projectRepository, TopicRepository topicRepository) {
        super(teamRepository, ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("name", ignoreCase())
        );

        this.organisationRepository = organisationRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public CompletableFuture<Team> update(Team updatedEntity) {
        CompletableFuture<Team> future = new CompletableFuture<>();
        final var id = updatedEntity.getId();
        if (id == null) throw new IllegalArgumentException("ID is null");
        final var entry = teamRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setName(updatedEntity.getName());
        entry.setLastModifiedDate(LocalDateTime.now());
        teamRepository.save(entry);

        future.complete(entry);
        return future;
    }

    @Override
    public CompletableFuture<Team> create(Team entity, Long organisationId) {
        final var result = this.create(entity);
        return result.thenApply(team -> addToOrganisation(team, organisationId));
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

    private CompletableFuture<Team> create(Team entity) {
        return this.exists(entity).thenCompose(exists -> {

            if (exists) {
                final var found = teamRepository.findOne(Example.of(entity, getExampleMatcher()));
                if (found.isPresent()) return CompletableFuture.completedFuture(found.get());
            }

            entity.setCreatedDate(LocalDateTime.now());
            entity.setLastModifiedDate(LocalDateTime.now());
            return CompletableFuture.completedFuture(teamRepository.save(entity));
        });
    }

    private Team addToOrganisation(Team team, Long organisationId) {
        final var organisation = this.organisationRepository.findById(organisationId).orElseThrow(() -> new ConnectEntryException("Can't find organisation with id: " + organisationId));
        team.setOrganisation(organisation);
        return teamRepository.save(team);
    }
}
