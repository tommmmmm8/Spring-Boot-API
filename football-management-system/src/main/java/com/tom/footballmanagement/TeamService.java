package com.tom.footballmanagement;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.tom.footballmanagement.Position.STRIKER;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.JUNE;

@Service
public class TeamService {

    private ArrayList<Player> players = new ArrayList<Player>();

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player findPlayerById(long id) {
        for (Player player : players) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }

    public Player addPlayer(Player player){
        players.add(player);
        return player;
    }

    public Boolean deletePlayer(long id) {
        Player playerById = findPlayerById(id);

        try {
            players.remove(playerById);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Player getPlayer(long id) {
        return findPlayerById(id);
    }

    public Player modifyPlayer(long id, Map<String, Object> updates) {
        Player playerToModify = findPlayerById(id);
        if (playerToModify == null)
            return null;

        updates.forEach((key, value)-> {
                switch (key) {
                    case "firstName" -> playerToModify.setFirstName((String) value);
                    case "lastName" -> playerToModify.setLastName((String) value);
                    case "dateOfBirth" -> playerToModify.setDateOfBirth(LocalDate.parse((String) value));
                    case "nationality" -> playerToModify.setNationality((String) value);
                    case "position" -> playerToModify.setPosition((Position) value);
                    case "team" -> playerToModify.setTeam((String) value);
                    case "contractEndDate" -> playerToModify.setContractEndDate((LocalDate) value);
                }
        });

        return playerToModify;
    }

    public Player addMbappe() {
        Player player = new Player(
                1,
                "Kylian",
                "Mbappe",
                LocalDate.of(1998, DECEMBER, 20),
                "French",
                STRIKER,
                "Real Madrid",
                LocalDate.of(2029, JUNE, 30)
        );
        players.add(player);
        return player;
    }
}
