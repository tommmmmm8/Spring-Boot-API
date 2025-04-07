package com.tom.footballmanagement.DTO;

import java.time.LocalDate;

public record ManagerResponseDTO(
        Long coach_id,
        String first_name,
        String last_name,
        LocalDate date_of_birth,
        String nationality,
        String team
) {
}