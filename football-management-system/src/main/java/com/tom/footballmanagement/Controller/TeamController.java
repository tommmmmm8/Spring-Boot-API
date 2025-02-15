package com.tom.footballmanagement.Controller;

import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Entity.Team;
import com.tom.footballmanagement.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Teams")
public class TeamController {

    public final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // Add a team
    @PostMapping("")
    public Team addTeam(@RequestBody Team team) {
        return teamService.addTeam(team);
    }

    // Remove a team
    @DeleteMapping("/{team_id}")
    public String removeTeam(@PathVariable Long team_id) {
        return teamService.removeTeam(team_id);
    }

    // Get players of a specific team
    @GetMapping("/{team_id}/players")
    public List<Player> getPlayersByTeam(@PathVariable Long team_id, @RequestBody Team team) {
        return teamService.getPlayersByTeam(team_id);
    }

    // Remove a specific player from a specific team
    @DeleteMapping("/{team_id}/players/{player_id}")
    public String deletePlayerFromTeam(@PathVariable Long team_id, @PathVariable Long player_id) {
        return teamService.removePlayerFromTeam(team_id, player_id);
    }
}
