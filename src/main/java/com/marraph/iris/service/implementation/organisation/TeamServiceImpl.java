package com.marraph.iris.service.implementation.organisation;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.Team;
import com.marraph.iris.repository.TeamRepository;
import com.marraph.iris.service.organisation.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public final class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final ExampleMatcher modelMatcher;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;

        this.modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("name", ignoreCase());
    }

    @Override
    public CompletableFuture<Team> create(Team entity) {
        return this.exists(entity).thenCompose(exists -> {
            if (exists) return CompletableFuture.completedFuture(entity);
            return CompletableFuture.completedFuture(teamRepository.save(entity));
        });
    }

    @Override
    public CompletableFuture<Team> update(Long id, Team updatedEntity) {
        CompletableFuture<Team> future = new CompletableFuture<>();
        final var entry = teamRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setName(updatedEntity.getName());
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
    public void delete(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public CompletableFuture<Boolean> exists(Team entity) {
        return CompletableFuture.completedFuture(teamRepository.exists(Example.of(entity, modelMatcher)));
    }
}