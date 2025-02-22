package com.tom.footballmanagement.DTO;

public record TeamDTO(Long team_id, String name, String stadium, CoachDTO coach, String league) {
}
