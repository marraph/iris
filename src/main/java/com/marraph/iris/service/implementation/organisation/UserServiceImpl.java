package com.marraph.iris.service.implementation.organisation;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.User;
import com.marraph.iris.repository.UserRepository;
import com.marraph.iris.service.organisation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public final class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ExampleMatcher modelMatcher;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

        this.modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("username", ignoreCase())
                .withMatcher("email", ignoreCase());
    }

    @Override
    public CompletableFuture<User> create(User entity) {
        return this.exists(entity).thenCompose(exists -> {
            if (exists) return CompletableFuture.completedFuture(entity);
            return CompletableFuture.completedFuture(userRepository.save(entity));
        });
    }

    @Override
    public CompletableFuture<User> update(Long id, User updatedEntity) {
        CompletableFuture<User> future = new CompletableFuture<>();
        final var entry = userRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setUsername(updatedEntity.getUsername());
        entry.setEmail(updatedEntity.getEmail());
        userRepository.save(entry);

        future.complete(entry);
        return future;
    }

    @Override
    public CompletableFuture<Optional<User>> getById(Long id) {
        return CompletableFuture.completedFuture(userRepository.findById(id).or(() -> {
            throw new EntryNotFoundException(id);
        }));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public CompletableFuture<Boolean> exists(User entity) {
        return CompletableFuture.completedFuture(userRepository.exists(Example.of(entity, modelMatcher)));
    }
}
