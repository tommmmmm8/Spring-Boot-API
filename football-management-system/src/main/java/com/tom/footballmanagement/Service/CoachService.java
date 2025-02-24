package com.tom.footballmanagement.Service;

import com.tom.footballmanagement.Entity.Coach;
import com.tom.footballmanagement.Entity.Team;
import com.tom.footballmanagement.Repository.CoachRepository;
import com.tom.footballmanagement.Repository.TeamRepository;
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
public class CoachService {
    private final CoachRepository coachRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public CoachService(CoachRepository coachRepository, TeamRepository teamRepository) {
        this.coachRepository = coachRepository;
        this.teamRepository = teamRepository;
    }

    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }

    public Coach getCoach(Long id) {
        return coachRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coach not found"));
    }

    public Coach addCoach(Coach coach) {
        if (coach.getId() != null)
            if (coachRepository.existsById(coach.getId()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Coach with id: " + coach.getId() + " already exists");

        return coachRepository.save(coach);
    }

    public ResponseEntity<String> removeCoach(Long id) {
        Coach coach = coachRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coach not found"));

        teamRepository.findBycoach(coach).forEach(team -> team.setCoach(null));

        coachRepository.deleteById(id);
        return ResponseEntity.ok(String.format("Coach with id: %d was deleted", id));
    }

    public Coach modifyCoach(Long id, Map<String, Object> updates) {
        Coach coach = coachRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coach not found"));

        updates.forEach( (key, value) -> {
            Field field = ReflectionUtils.findField(Coach.class, key);
            if (field != null) {
                ReflectionUtils.makeAccessible(field);
                if (key.equals("team")) {
                    Map<?,?> map = (Map<?, ?>) value;
                    if (map != null) {
                        if (map.containsKey("id") && map.get("id") != null) {
                            coach.setTeam(teamRepository.findById(Long.valueOf((Integer) map.get("id")))
                                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team with id: " + map.get("id") + " not found")));
                        }
                        else if (map.containsKey("name") && map.get("name") != null)
                            ReflectionUtils.setField(field, coach, teamRepository.findByName(map.get("name").toString()));
                    } else
                        coach.setTeam(null);
                }
                else
                    ReflectionUtils.setField(field, coach, value);
            } else {
                System.out.println("Unable to do partial update field/property: " + key);
            }
        });
        return coachRepository.save(coach);
    }
}
