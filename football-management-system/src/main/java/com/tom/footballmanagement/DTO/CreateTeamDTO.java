package com.tom.footballmanagement.DTO;

import com.tom.footballmanagement.Entity.Manager;
import com.tom.footballmanagement.Entity.Team;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record CreateTeamDTO(
        @NotEmpty(message = "Name is required") String name,
        LocalDate founded_year,
        @NotEmpty(message = "Stadium is required") String stadium,
        Manager manager,
        @NotEmpty(message = "League is required") String league
) {
    public Team toTeam() {
        Team team = new Team();
        team.setName(name);
        team.setFounded_year(founded_year);
        team.setStadium(stadium);
        team.setManager(manager);
        team.setLeague(league);
        return team;
    }
}

