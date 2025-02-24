package com.tom.footballmanagement.DTO;

import com.tom.footballmanagement.Entity.Team;

import java.time.LocalDate;

public record TeamResponseDTO(Long team_id,
                              String name,
                              LocalDate founded_year,
                              String stadium,
                              CoachResponseDTO coach,
                              String league) {
}
