package com.tom.footballmanagement.Repository;

import com.tom.footballmanagement.Entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach, Long> {
}
