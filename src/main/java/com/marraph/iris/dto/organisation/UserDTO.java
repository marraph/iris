package com.marraph.iris.dto.organisation;

import java.util.Set;

public record UserDTO(
        Long id,
        String name,
        String password,
        String email,
        Set<TeamDTO> teams
) {
}