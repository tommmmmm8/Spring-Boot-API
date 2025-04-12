package com.tom.footballmanagement.Service;

import com.tom.footballmanagement.DTO.CreatePlayerDTO;
import com.tom.footballmanagement.DTO.PlayerResponseDTO;
import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Repository.PlayerRepository;
import com.tom.footballmanagement.Repository.TeamRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.tom.footballmanagement.Enum.Position.STRIKER;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.JUNE;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    private boolean validateId(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id cannot be null");
        if (id < 0)
            throw new IllegalArgumentException("Id cannot be negative");
        return true;
    }

    public List<PlayerResponseDTO> getAllPlayers() {
        return playerRepository.findAll().stream().map(Player::toResponseDTO).toList();
    }

    public PlayerResponseDTO getPlayer(Long id) {
        validateId(id);

        return playerRepository.findById(id)
                .map(Player::toResponseDTO)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
    }

    public PlayerResponseDTO addMbappe() {
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
        return playerRepository.save(player).toResponseDTO();
    }

    public PlayerResponseDTO addPlayer(CreatePlayerDTO createPlayerDTO){
        if (createPlayerDTO == null)
            throw new IllegalArgumentException("Player cannot be null");

        return playerRepository.save(createPlayerDTO.toPlayer()).toResponseDTO();
    }

    public PlayerResponseDTO modifyPlayer(Long id, Map<String, Object> updates) {
        validateId(id);
        Player playerToModify = playerRepository.findById(id)
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Player.class, key);
            if (field != null) {
                ReflectionUtils.makeAccessible(field);
                // team needs special handling
                if (key.equals("team")) {
                    Map<?,?> map =  (Map<?,?>) value;
                    if (map != null) {
                        if (map.containsKey("id") && map.get("id") != null) {
                            Long teamId = ((Number) map.get("id")).longValue();
                            validateId(teamId);
                            ReflectionUtils.setField(field, playerToModify, teamRepository.findById(teamId)
                                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team with id: " + map.get("id") + " doesn't exist")));
                        }
                        else if (map.containsKey("name") && map.get("name") != null)
                            ReflectionUtils.setField(field, playerToModify, teamRepository.findByName(map.get("name").toString()));
                    } else
                        playerToModify.setTeam(null);

                } else {
                    ReflectionUtils.setField(field, playerToModify, value);
                }
            } else {
                System.out.println("Unable to do partial update field/property: " + key);
            }
        });
        return playerRepository.save(playerToModify).toResponseDTO();
    }

    public ResponseEntity<String> removePlayer(Long id) {
        if (playerRepository.existsById(id) == false)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");

        playerRepository.deleteById(id);
        if (playerRepository.existsById(id) == false)
            return ResponseEntity.ok(String.format("Player with id: %d was deleted", id));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Player with id: %d was not deleted", id));
    }


}
