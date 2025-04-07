package com.tom.footballmanagement.Controller;

import com.tom.footballmanagement.DTO.CreateManagerDTO;
import com.tom.footballmanagement.DTO.ManagerResponseDTO;
import com.tom.footballmanagement.Entity.Manager;
import com.tom.footballmanagement.Service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/managers")
    public List<ManagerResponseDTO> getAllCoaches() {
        return managerService.getAllCoaches();
    }

    @GetMapping("/managers/{id}")
    public ManagerResponseDTO getCoach(@PathVariable Long id) {
        return managerService.getCoach(id);
    }

    @PostMapping("/managers")
    public ManagerResponseDTO addCoach(@RequestBody CreateManagerDTO createManagerDTO) {
        return managerService.addCoach(createManagerDTO);
    }

    @PatchMapping("/managers/{id}")
    public ManagerResponseDTO modifyCoach(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return managerService.modifyCoach(id, updates);
    }

    @DeleteMapping("/managers/{id}")
    public ResponseEntity<String> removeCoach(@PathVariable Long id) {
        return managerService.removeCoach(id);
    }
}
