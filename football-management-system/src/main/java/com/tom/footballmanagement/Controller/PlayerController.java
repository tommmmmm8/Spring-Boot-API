package com.tom.footballmanagement.Controller;

import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Service.PlayerService;
import com.tom.footballmanagement.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    // Get a player by id
    @GetMapping("/players/{id}")
    public Player getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    // Add a player
    @PostMapping("/players")
    public Player addPlayer(@RequestBody Player player) {
        return playerService.addPlayer(player);
    }

    // Add mbappe
    @PostMapping("/players/mbappe")
    public Player addMbappe() {
        return playerService.addMbappe();
    }

    // Modify a specific player
    @PatchMapping("/players/{id}")
    public Player modifyPlayer(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return playerService.modifyPlayer(id, updates);
    }

    // Delete a specific player
    @DeleteMapping("/players/{id}")
    public String removePlayer(@PathVariable Long id) {
        return playerService.removePlayer(id);
    }
}
