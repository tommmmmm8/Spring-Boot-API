package com.tom.footballmanagement.DTO;

import com.tom.footballmanagement.Enum.Position;

import java.time.LocalDate;

public record PlayerResponseDTO(
        Long id,
        String first_name,
        String last_name,
        LocalDate date_of_birth,
        String nationality,
        Position position,
        String team,
        LocalDate contract_end_date
) {
}
