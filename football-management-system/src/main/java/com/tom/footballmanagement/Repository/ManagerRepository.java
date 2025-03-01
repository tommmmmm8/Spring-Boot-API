package com.tom.footballmanagement.Repository;

import com.tom.footballmanagement.Entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
