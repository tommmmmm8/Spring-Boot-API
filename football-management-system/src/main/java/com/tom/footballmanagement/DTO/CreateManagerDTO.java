package com.tom.footballmanagement.DTO;

import com.tom.footballmanagement.Entity.Manager;
import com.tom.footballmanagement.Entity.Team;

import java.time.LocalDate;

public record CreateManagerDTO(
        String first_name,
        String last_name,
        LocalDate date_of_birth,
        String nationality,
        Team team
) {
    public Manager toManager() {
        Manager manager = new Manager();
        manager.setFirst_name(first_name);
        manager.setLast_name(last_name);
        manager.setDate_of_birth(date_of_birth);
        manager.setNationality(nationality);
        manager.setTeam(team);
        return manager;
    }
}
