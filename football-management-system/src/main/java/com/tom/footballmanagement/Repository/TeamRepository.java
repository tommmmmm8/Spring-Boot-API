package com.tom.footballmanagement.Repository;

import com.tom.footballmanagement.Entity.Manager;
import com.tom.footballmanagement.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String name);

    List<Team> findByManager(Manager manager);
}
