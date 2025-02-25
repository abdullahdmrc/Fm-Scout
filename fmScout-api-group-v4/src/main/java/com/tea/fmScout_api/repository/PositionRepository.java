package com.tea.fmScout_api.repository;

import com.tea.fmScout_api.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    @Query("SELECT p FROM Position p WHERE p.positionName = :roleName")
    Optional<Position> findByPositionName(String roleName);
    @Query("SELECT p FROM Position p WHERE p.positionId = :positionId")
    Optional<Position> findByPositionId(Long positionId);
}
