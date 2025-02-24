package com.tom.footballmanagement.Service;

import com.tom.footballmanagement.Entity.Coach;
import com.tom.footballmanagement.Entity.Player;
import com.tom.footballmanagement.Repository.CoachRepository;
import com.tom.footballmanagement.Repository.PlayerRepository;
import com.tom.footballmanagement.Repository.TeamRepository;
import com.tom.footballmanagement.Entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final CoachRepository coachRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository, CoachRepository coachRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.coachRepository = coachRepository;
    }

    public boolean teamExists(Long team_id) {
        return teamRepository.existsById(team_id);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public List<Player> getPlayersByTeam(Long team_id) {
        Team team = teamRepository.findById(team_id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));

        return playerRepository.getPlayersByTeam(team);
    }

    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
    }

    public Team addTeam(Team team) {
        if (team.getId() != null)
            if (teamExists(team.getId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Team: " + team.getName() + " already exists");
        return teamRepository.save(team);
    }

    public Team modifyTeam(Long team_id, Map<String, Object> updates) {
        Team team = teamRepository.findById(team_id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));

        updates.forEach( (key, value) -> {
            Field field = ReflectionUtils.findField(Team.class, key);
            if (field != null) {
                ReflectionUtils.makeAccessible(field);
                if (key.equals("coach")) {
                    Map<?,?> map = (Map<?, ?>) value;
                    if (map != null) {
                        if (map.containsKey("id") && map.get("id") != null) {
                            team.setCoach(coachRepository.findById(Long.valueOf((Integer) map.get("id")))
                                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coach with id: " + map.get("id") + " not found")));
                        }
                    } else
                        team.setCoach(null);
                } else
                    ReflectionUtils.setField(field, team, value);
            } else {
                System.out.println("Unable to do partial update field/property: " + key);
            }
        });
        return team;
    }

    public ResponseEntity<String> removeTeam(Long team_id) {
        Team team = teamRepository.findById(team_id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));

        // All players from the removed team will be without a team
        playerRepository.getPlayersByTeam(team).forEach( player -> player.setTeam(null));

        teamRepository.deleteById(team_id);
        return ResponseEntity.ok(String.format("Team with id: %d was deleted", team_id));
    }
}
