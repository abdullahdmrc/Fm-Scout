package com.tea.fmScout_api.repository;

import com.tea.fmScout_api.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
    @Query("SELECT l FROM League l WHERE l.leagueName = :leagueName")
    Optional<League> findByLeagueName(String leagueName);

    boolean existsByLeagueName(String leagueName);
}

