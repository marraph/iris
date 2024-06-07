package com.marraph.iris.service.implementation.task;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.task.Topic;
import com.marraph.iris.repository.TopicRepository;
import com.marraph.iris.service.plain.task.TopicService;
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
public final class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final ExampleMatcher modelMatcher;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;

        this.modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("title", ignoreCase())
                .withMatcher("hexCode", ignoreCase());
    }

    @Override
    public CompletableFuture<Topic> create(Topic entity) {
        return this.exists(entity).thenCompose(exists -> {

            if (exists) {
                final var found = topicRepository.findOne(Example.of(entity, modelMatcher));
                if (found.isPresent()) return CompletableFuture.completedFuture(found.get());
            }

            entity.setCreatedDate(LocalDateTime.now());
            entity.setLastModifiedDate(LocalDateTime.now());
            return CompletableFuture.completedFuture(topicRepository.save(entity));
        });
    }

    @Override
    public CompletableFuture<Topic> update(Long id, Topic updatedEntity) {
        CompletableFuture<Topic> future = new CompletableFuture<>();
        final var entry = topicRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setTitle(updatedEntity.getTitle());
        entry.setHexCode(updatedEntity.getHexCode());
        topicRepository.save(entry);

        future.complete(entry);
        return future;
    }

    @Override
    public CompletableFuture<Optional<Topic>> getById(Long id) {
        return CompletableFuture.completedFuture(topicRepository.findById(id).or(() -> {
            throw new EntryNotFoundException(id);
        }));
    }

    @Override
    public CompletableFuture<List<Topic>> getAll() {
        return CompletableFuture.completedFuture(topicRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    public CompletableFuture<Boolean> exists(Topic entity) {
        return CompletableFuture.completedFuture(topicRepository.exists(Example.of(entity, modelMatcher)));
    }
}
