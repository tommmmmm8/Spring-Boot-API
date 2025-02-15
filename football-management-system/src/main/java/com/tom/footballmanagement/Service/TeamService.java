package com.tom.footballmanagement.Service;

import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Repository.PlayerRepository;
import com.tom.footballmanagement.Repository.TeamRepository;
import com.tom.footballmanagement.Entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayersByTeam(Long team_id) {
        Team team = teamRepository.findById(team_id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team does not exist"));

        return playerRepository.getPlayersByTeam(team);
    }

    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    public String removeTeam(Long team_id) {
        Team team = teamRepository.findById(team_id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));

        // All players from the removed team will be without a team
        playerRepository.getPlayersByTeam(team).forEach( player -> player.setTeam(null));

        teamRepository.deleteById(team_id);
        return String.format("Team with id: %d was deleted", team_id);
    }

    public String removePlayerFromTeam(Long teamId, Long playerId) {
        return null;
    }
}
