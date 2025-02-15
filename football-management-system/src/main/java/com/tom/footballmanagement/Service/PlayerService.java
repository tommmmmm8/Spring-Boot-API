package com.tom.footballmanagement.Service;

import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Enum.Position;
import com.tom.footballmanagement.Repository.PlayerRepository;
import com.tom.footballmanagement.Entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.tom.footballmanagement.Enum.Position.STRIKER;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.JUNE;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

//    public ResponseEntity<Void> playerExists(Long id) {
//        boolean exists = playerRepository.existsById(id);
//        if (exists)
//            return ResponseEntity.noContent().build(); // 204 No Content
//        else
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
//    }

    public Player addPlayer(Player player){
        // checking if the id was specified in the json but already exists in the table
        if (player.getId() != null)
            if (playerRepository.existsById(player.getId()))
                throw new IllegalStateException("Player with id: " + player.getId() + " already exists");

        System.out.println("Player was saved");
        return playerRepository.save(player);
    }

    public String deletePlayer(Long id) {
        if (!playerRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");

        playerRepository.deleteById(id);
        return String.format("Player with id: %d was deleted", id);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
    }

    public Player modifyPlayer(Long id, Map<String, Object> updates) {
        Player playerToModify = playerRepository.findById(id)
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));

        updates.forEach((key, value)-> {
                switch (key) {
                    case "firstName" -> playerToModify.setFirst_name((String) value);
                    case "lastName" -> playerToModify.setLast_name((String) value);
                    case "dateOfBirth" -> playerToModify.setDate_of_birth(LocalDate.parse((String) value));
                    case "nationality" -> playerToModify.setNationality((String) value);
                    case "position" -> playerToModify.setPosition((Position) value);
                    case "team" -> playerToModify.setTeam((Team) value);
                    case "contractEndDate" -> playerToModify.setContractEndDate((LocalDate) value);
                }
        });
        return playerRepository.save(playerToModify);
    }

    public Player addMbappe() {
        Player player = new Player(
                null,
                "Kylian",
                "Mbappe",
                LocalDate.of(1998, DECEMBER, 20),
                "French",
                STRIKER,
                null,
                LocalDate.of(2029, JUNE, 30)
        );
        return playerRepository.save(player);
    }

    public Player addPlayerToTeam(Long teamId) {
        return null;
    }
}
