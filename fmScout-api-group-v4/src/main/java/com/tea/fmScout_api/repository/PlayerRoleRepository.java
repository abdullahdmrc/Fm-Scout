package com.tea.fmScout_api.repository;

import com.tea.fmScout_api.model.PlayerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRoleRepository extends JpaRepository<PlayerRole, Long> {
    void deleteByPlayer_PlayerId(Long playerId);

    @Query("SELECT pr.position.positionName FROM PlayerRole pr WHERE pr.player.playerId = :playerId")
    List<String> findPosition_PositionNamesByPlayer_PlayerId(Long playerId);
}
