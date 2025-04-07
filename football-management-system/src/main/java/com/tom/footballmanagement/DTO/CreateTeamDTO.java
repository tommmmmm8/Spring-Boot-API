package com.tom.footballmanagement.DTO;

import java.time.LocalDate;

public record CreateTeamDTO(
        String name,
        LocalDate founded_year,
        String stadium,
        String manager,
        String league
) {
}
