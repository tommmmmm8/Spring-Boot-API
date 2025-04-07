package com.tom.footballmanagement.DTO;

import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Entity.Team;
import com.tom.footballmanagement.Enum.Position;

import java.time.LocalDate;

public record CreatePlayerDTO(
        String first_name,
        String last_name,
        LocalDate date_of_birth,
        String nationality,
        Position position,
        Team team,
        LocalDate contract_end_date
) {
    public Player toPlayer() {
        Player player = new Player();
        player.setFirst_name(first_name);
        player.setLast_name(last_name);
        player.setDate_of_birth(date_of_birth);
        player.setNationality(nationality);
        player.setPosition(position);
        player.setTeam(team);
        player.setContract_end_date(contract_end_date);
        return player;
    }
}
