package com.tom.footballmanagement.Controller;

import com.tom.footballmanagement.DTO.CreatePlayerDTO;
import com.tom.footballmanagement.DTO.PlayerResponseDTO;
import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Service.PlayerService;
import com.tom.footballmanagement.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<PlayerResponseDTO> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    // Get a player by id
    @GetMapping("/{id}")
    public PlayerResponseDTO getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    // Add a player
    @PostMapping
    public PlayerResponseDTO addPlayer(@RequestBody CreatePlayerDTO createPlayerDTO) {
        return playerService.addPlayer(createPlayerDTO);
    }

    // Add mbappe
    @PostMapping("/mbappe")
    public PlayerResponseDTO addMbappe() {
        return playerService.addMbappe();
    }

    // Modify a specific player
    @PatchMapping("/{id}")
    public PlayerResponseDTO modifyPlayer(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return playerService.modifyPlayer(id, updates);
    }

    // Delete a specific player
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removePlayer(@PathVariable Long id) {
        return playerService.removePlayer(id);
    }
}
