package com.tom.footballmanagement.DTO;

import java.time.LocalDate;

public record TeamResponseDTO(
        Long team_id,
        String name,
        LocalDate founded_year,
        String stadium,
        String manager,
        String league
) {
}
