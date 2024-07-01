package com.marraph.iris.service.implementation.task;

import com.marraph.iris.exception.ConnectEntryException;
import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.task.Topic;
import com.marraph.iris.repository.TeamRepository;
import com.marraph.iris.repository.TopicRepository;
import com.marraph.iris.service.implementation.AbstractServiceImpl;
import com.marraph.iris.service.plain.task.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public final class TopicServiceImpl extends AbstractServiceImpl<Topic> implements TopicService {

    private final TopicRepository topicRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, TeamRepository teamRepository) {
        super(topicRepository, ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("title", ignoreCase())
                .withMatcher("hexCode", ignoreCase())
        );

        this.topicRepository = topicRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public CompletableFuture<Topic> update(Topic updatedEntity) {
        CompletableFuture<Topic> future = new CompletableFuture<>();
        final var id = updatedEntity.getId();

        if (id == null) throw new IllegalArgumentException("ID is null");
        final var entry = topicRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setTitle(updatedEntity.getTitle());
        entry.setHexCode(updatedEntity.getHexCode());
        entry.setLastModifiedDate(LocalDateTime.now());
        topicRepository.save(entry);

        future.complete(entry);
        return future;
    }

    @Override
    public CompletableFuture<Topic> create(Topic entity, Long teamId) {
        final var result = this.create(entity);
        result.thenAccept(topic -> addToTeam(topic, teamId));
        return result;
    }

    private CompletableFuture<Topic> create(Topic entity) {
        return this.exists(entity).thenCompose(exists -> {

            if (exists) {
                final var found = topicRepository.findOne(Example.of(entity, getExampleMatcher()));
                if (found.isPresent()) return CompletableFuture.completedFuture(found.get());
            }

            entity.setCreatedDate(LocalDateTime.now());
            entity.setLastModifiedDate(LocalDateTime.now());
            return CompletableFuture.completedFuture(topicRepository.save(entity));
        });
    }

    private void addToTeam(Topic topic, Long teamId) {
        final var team = this.teamRepository.findById(teamId).orElseThrow(() -> new ConnectEntryException("Can't find team with id: " + teamId));
        team.getTopics().add(topic);
        this.teamRepository.save(team);
    }
}
