package com.tea.fmScout_api.repository;

import com.tea.fmScout_api.model.Club;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    @Query("SELECT c FROM Club c WHERE c.clubName = ?1")
    public Optional<Club> findByClubName(String name);

    boolean existsByClubName(String clubName);

    @Query("SELECT c FROM Club c WHERE c.id = ?1")
    Optional<Club> findClubById(Long clubId);

    @Query("SELECT c FROM Club c WHERE c.league.leagueId = :leagueId")
    List<Club> findClubsByLeague_LeagueId(Long leagueId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM clubs WHERE id = :clubId", nativeQuery = true)
    void deleteClubById(@Param("clubId") Long clubId);

    @Procedure(name = "resetplayervalues")
    void resetPlayerValues(@Param("club_id_param") Long clubId);

}
