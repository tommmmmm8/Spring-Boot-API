package com.tom.footballmanagement.Controller;

import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Entity.Team;
import com.tom.footballmanagement.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teams")
public class TeamController {

    public final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // Get all teams
    @GetMapping
    public List<Team> getAllTeams() { return teamService.getAllTeams(); }

    // Get players of a specific team
    @GetMapping("/{team_id}/players")
    public List<Player> getPlayersByTeam(@PathVariable Long team_id) {
        return teamService.getPlayersByTeam(team_id);
    }

    @GetMapping("/{team_id}")
    public Team getTeamById(@PathVariable Long team_id) {
        return teamService.getTeamById(team_id);
    }

    // Add a team
    @PostMapping
    public Team addTeam(@RequestBody Team team) {
        return teamService.addTeam(team);
    }

    // Modify a team
    @PatchMapping("/{team_id}")
    public Team modifyTeam(@PathVariable Long team_id, @RequestBody Map<String, Object> updates) { return teamService.modifyTeam(team_id, updates); }

    // Remove a team
    @DeleteMapping("/{team_id}")
    public String removeTeam(@PathVariable Long team_id) {
        return teamService.removeTeam(team_id);
    }
}
