package com.tom.footballmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController (TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/players")
    public ArrayList<Player> getPlayers() {
        return teamService.getPlayers();
    }

    @GetMapping("/players/{id}")
    public Player getPlayer(@PathVariable long id) {
        return teamService.getPlayer(id);
    }

    @PostMapping("/players")
    public Player addPlayer(@RequestBody Player player) {
        return teamService.addPlayer(player);
    }

    @PostMapping("/players/mbappe")
    public Player addMbappe() {
        return teamService.addMbappe();
    }

    @PatchMapping("/players/{id}")
    public Player modifyPlayer(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        return teamService.modifyPlayer(id, updates);
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable long id) {
        teamService.deletePlayer(id);
    }

}
