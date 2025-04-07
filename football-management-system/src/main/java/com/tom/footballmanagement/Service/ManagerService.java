package com.tom.footballmanagement.Service;

import com.tom.footballmanagement.DTO.ManagerResponseDTO;
import com.tom.footballmanagement.Entity.Manager;
import com.tom.footballmanagement.Repository.ManagerRepository;
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
import java.util.Optional;

@Service
@Transactional
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository, TeamRepository teamRepository) {
        this.managerRepository = managerRepository;
        this.teamRepository = teamRepository;
    }

    public List<ManagerResponseDTO> getAllCoaches() {
        return managerRepository.findAll().stream().map(Manager::toResponseDTO).toList();
    }

    public ManagerResponseDTO getCoach(Long id) {
        Optional<Manager> manager = managerRepository.findById(id);
        if (manager.isPresent())
            return manager.get().toResponseDTO();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager not found");
    }

    public ManagerResponseDTO addCoach(Manager manager) {
        if (manager.getId() != null)
            if (managerRepository.existsById(manager.getId()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Manager with id: " + manager.getId() + " already exists");

        return managerRepository.save(manager).toResponseDTO();
    }

    public ManagerResponseDTO modifyCoach(Long id, Map<String, Object> updates) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager not found"));

        updates.forEach( (key, value) -> {
            Field field = ReflectionUtils.findField(Manager.class, key);
            if (field != null) {
                ReflectionUtils.makeAccessible(field);
                if (key.equals("team")) {
                    Map<?,?> map = (Map<?, ?>) value;
                    if (map != null) {
                        if (map.containsKey("id") && map.get("id") != null) {
                            manager.setTeam(teamRepository.findById(Long.valueOf((Integer) map.get("id")))
                                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team with id: " + map.get("id") + " not found")));
                        }
                        else if (map.containsKey("name") && map.get("name") != null)
                            ReflectionUtils.setField(field, manager, teamRepository.findByName(map.get("name").toString()));
                    } else
                        manager.setTeam(null);
                }
                else
                    ReflectionUtils.setField(field, manager, value);
            } else {
                System.out.println("Unable to do partial update field/property: " + key);
            }
        });
        return managerRepository.save(manager).toResponseDTO();
    }

    public ResponseEntity<String> removeCoach(Long id) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager not found"));

        teamRepository.findByManager(manager).forEach(team -> team.setCoach(null));

        managerRepository.deleteById(id);
        return ResponseEntity.ok(String.format("Manager with id: %d was deleted", id));
    }
}
