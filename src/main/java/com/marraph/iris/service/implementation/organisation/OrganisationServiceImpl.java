package com.marraph.iris.service.implementation.organisation;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.Organisation;
import com.marraph.iris.repository.OrganisationRepository;
import com.marraph.iris.service.plain.organisation.OrganisationService;
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
public final class OrganisationServiceImpl implements OrganisationService {

    private final OrganisationRepository organisationRepository;
    private final ExampleMatcher modelMatcher;

    @Autowired
    public OrganisationServiceImpl(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;

        this.modelMatcher = ExampleMatcher.matching().withIgnorePaths("id").withMatcher("name", ignoreCase());
    }

    @Override
    public CompletableFuture<Organisation> create(Organisation entity) {
        return this.exists(entity).thenCompose(exists -> {

            if (exists) {
                final var found = organisationRepository.findOne(Example.of(entity, modelMatcher));
                if (found.isPresent()) return CompletableFuture.completedFuture(found.get());
            }

            entity.setCreatedDate(LocalDateTime.now());
            entity.setLastModifiedDate(LocalDateTime.now());
            return CompletableFuture.completedFuture(organisationRepository.save(entity));
        });
    }

    @Override
    public CompletableFuture<Organisation> update(Organisation updatedEntity) {
        CompletableFuture<Organisation> future = new CompletableFuture<>();
        final var id = updatedEntity.getId();
        if (id == null) throw new IllegalArgumentException("ID is null");
        final var entry = organisationRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setName(updatedEntity.getName());
        entry.setLastModifiedDate(LocalDateTime.now());
        organisationRepository.save(entry);

        future.complete(entry);
        return future;
    }

    @Override
    public CompletableFuture<Optional<Organisation>> getById(Long id) {
        return CompletableFuture.completedFuture(organisationRepository.findById(id).or(() -> {
            throw new EntryNotFoundException(id);
        }));
    }

    @Override
    public CompletableFuture<List<Organisation>> getAll() {
        return CompletableFuture.completedFuture(organisationRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        organisationRepository.deleteById(id);
    }

    @Override
    public CompletableFuture<Boolean> exists(Organisation entity) {
        return CompletableFuture.completedFuture(organisationRepository.exists(Example.of(entity, modelMatcher)));
    }
}