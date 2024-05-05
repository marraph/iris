package com.marraph.iris.service.implementation.organisation;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.organisation.Organisation;
import com.marraph.iris.repository.OrganisationRepository;
import com.marraph.iris.service.organisation.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class OrganisationServiceImpl implements OrganisationService {

    private final OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationServiceImpl(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    @Override
    public CompletableFuture<Organisation> create(Organisation entity) {
        return CompletableFuture.completedFuture(organisationRepository.save(entity));
    }

    @Override
    public CompletableFuture<Organisation> update(Long id, Organisation updatedEntity) {
        //TODO: CHECK IF ID EXISTS
        //TODO: CHECK IF ENTRY WITH GIVEN PROPERTIES ALREADY EXISTS
    }

    @Override
    public CompletableFuture<Optional<Organisation>> getById(Long id) {
        return CompletableFuture.completedFuture(organisationRepository.findById(id));
    }

    @Override
    public void delete(Long id) {
        organisationRepository.deleteById(id);
    }

    @Override
    public CompletableFuture<Boolean> exists(Organisation entity) {
        return CompletableFuture.completedFuture(organisationRepository.exists(Example.of(entity)));
    }
}
