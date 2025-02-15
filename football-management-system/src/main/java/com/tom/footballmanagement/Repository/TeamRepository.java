package com.tom.footballmanagement.Repository;

import com.tom.footballmanagement.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
