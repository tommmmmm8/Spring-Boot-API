package com.tom.footballmanagement.DTO;

import com.tom.footballmanagement.Entity.Coach;

import java.time.LocalDate;

public record CoachResponseDTO(Long coach_id,
                               String first_name,
                               String last_name,
                               LocalDate date_of_birth,
                               String nationality,
                               Long team_id) {
}