package com.tea.fmScout_api.repository;

import com.tea.fmScout_api.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    @Query("SELECT m FROM Manager m WHERE m.managerName = :managerName")
    Optional<Manager> findByManagerName(String managerName);

    boolean existsByManagerName(String managerName);
}
