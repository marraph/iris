package com.marraph.iris.data.dto.organisation;

public record TeamDTO(
        Long id,
        String name,
        OrganisationDTO organisation
) {
}