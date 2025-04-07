package com.tom.footballmanagement.DTO;

import com.tom.footballmanagement.Entity.Manager;
import com.tom.footballmanagement.Entity.Team;

import java.time.LocalDate;

public record CreateTeamDTO(
        String name,
        LocalDate founded_year,
        String stadium,
        Manager manager,
        String league
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

