package com.marraph.iris.dto.organisation;

public record TeamDTO(
        Long id,
        String name,
        OrganisationDTO organisation
) {
}