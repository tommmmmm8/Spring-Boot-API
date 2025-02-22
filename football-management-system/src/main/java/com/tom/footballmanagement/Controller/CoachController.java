package com.tom.footballmanagement.Controller;

import com.tom.footballmanagement.Entity.Coach;
import com.tom.footballmanagement.Service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CoachController {

    private final CoachService coachService;

    @Autowired
    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping("/coaches")
    public List<Coach> getAllCoaches() {
        return coachService.getAllCoaches();
    }

    @GetMapping("/coaches/{id}")
    public Coach getCoach(@PathVariable Long id) {
        return coachService.getCoach(id);
    }

    @PostMapping("/coaches")
    public Coach addCoach(@RequestBody Coach coach) {
        return coachService.addCoach(coach);
    }

    @PatchMapping("/coaches/{id}")
    public Coach modifyCoach(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return coachService.modifyCoach(id, updates);
    }

    @DeleteMapping("/coaches/{id}")
    public String removeCoach(@PathVariable Long id) {
        return coachService.removeCoach(id);
    }

}
