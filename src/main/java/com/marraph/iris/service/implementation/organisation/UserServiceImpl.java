package com.marraph.iris.service.implementation.organisation;

import com.marraph.iris.exception.ConnectEntryException;
import com.marraph.iris.exception.EmailInUseException;
import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.User;
import com.marraph.iris.repository.TeamRepository;
import com.marraph.iris.repository.UserRepository;
import com.marraph.iris.service.implementation.AbstractServiceImpl;
import com.marraph.iris.service.plain.organisation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public final class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ExampleMatcher emailMatcher;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TeamRepository teamRepository) {
        super(userRepository, ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("username", ignoreCase())
                .withMatcher("email", ignoreCase())
        );

        this.userRepository = userRepository;
        this.teamRepository = teamRepository;

        this.emailMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("email", ignoreCase());
    }

    @Override
    public CompletableFuture<User> create(User entity) {

        this.emailInUse(entity).thenAccept(IsAlreadyInUse -> {
            if (IsAlreadyInUse) throw new EmailInUseException(entity.getEmail());
        });

        return this.exists(entity).thenCompose(exists -> {

            if (!exists) {
                final var found = userRepository.findOne(Example.of(entity, getExampleMatcher()));
                if (found.isPresent()) return CompletableFuture.completedFuture(found.get());
            }

            return CompletableFuture.completedFuture(userRepository.save(entity));
        });
    }

    @Override
    public CompletableFuture<User> update(User updatedEntity) {

        this.emailInUse(updatedEntity).thenAccept(IsAlreadyInUse -> {
            if (IsAlreadyInUse) throw new EmailInUseException(updatedEntity.getEmail());
        });

        CompletableFuture<User> future = new CompletableFuture<>();
        final var id = updatedEntity.getId();
        final var entry = userRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setName(updatedEntity.getName());
        entry.setEmail(entry.getEmail());
        entry.setPassword(entry.getPassword());
        userRepository.save(entry);

        future.complete(entry);
        return future;
    }

    @Override
    public CompletableFuture<Boolean> emailInUse(User entity) {
        return CompletableFuture.completedFuture(userRepository.exists(Example.of(entity, emailMatcher)));
    }

    @Override
    public CompletableFuture<User> addToTeam(Long userId, Long teamId) {
        final var team = teamRepository.findById(teamId).orElseThrow(() -> new ConnectEntryException("Can't find team with id: " + teamId));
        final var user = userRepository.findById(userId).orElseThrow(() -> new ConnectEntryException("Can't find team with id: " + userId));

        user.getTeams().add(team);
        final var updatedUser = userRepository.save(user);

        return CompletableFuture.completedFuture(updatedUser);
    }
}
