package com.tom.footballmanagement.Controller;

import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
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
    public String deletePlayer(@PathVariable Long id) {
        return playerService.deletePlayer(id);
    }

    // Add a player to a specific team
    @PostMapping("/{team_id}/players")
    public Player addPlayerToTeam(@PathVariable Long team_id) {
        return playerService.addPlayerToTeam(team_id);
    }
}
