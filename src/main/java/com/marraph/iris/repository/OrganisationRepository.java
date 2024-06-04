package com.marraph.iris.repository;

import com.marraph.iris.model.organisation.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
}