package com.marraph.iris.repository;

import com.marraph.iris.service.organisation.OrganisationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends JpaRepository<OrganisationService, Long> {
}