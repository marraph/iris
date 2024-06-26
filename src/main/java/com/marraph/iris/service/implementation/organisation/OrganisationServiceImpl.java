package com.marraph.iris.service.implementation.organisation;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.Organisation;
import com.marraph.iris.repository.OrganisationRepository;
import com.marraph.iris.service.implementation.AbstractServiceImpl;
import com.marraph.iris.service.plain.organisation.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public final class OrganisationServiceImpl extends AbstractServiceImpl<Organisation> implements OrganisationService {

    private final OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationServiceImpl(OrganisationRepository organisationRepository) {
        super(organisationRepository, ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("name", ignoreCase())
        );

        this.organisationRepository = organisationRepository;
    }

    @Override
    public CompletableFuture<Organisation> create(Organisation entity) {
        return this.exists(entity).thenCompose(exists -> {

            if (exists) {
                final var found = organisationRepository.findOne(Example.of(entity, getExampleMatcher()));
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

}