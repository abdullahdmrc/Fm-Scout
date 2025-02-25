package com.tea.fmScout_api.repository;

import com.tea.fmScout_api.model.Favourite;
import com.tea.fmScout_api.model.FootballPlayer;
import com.tea.fmScout_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    @Query("SELECT f FROM Favourite f WHERE f.user.userId = :userId")
    List<Favourite> findByUser_UserId(Long userId);

    @Query("SELECT f FROM Favourite f WHERE f.user.userId = :userId AND f.player.playerId = :playerId")
    Optional<Favourite> findByUser_UserIdAndPlayer_PlayerId(Long userId, Long playerId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Favourite f " +
            "WHERE f.user.userId = :userId AND " + "f.player.playerId = :playerId")
    boolean existsByUser_UserIdAndPlayer_PlayerId(Long userId, Long playerId);

    @Query("SELECT f.player FROM Favourite f WHERE f.user.userId = :userId")
    List<FootballPlayer> findFootballPlayerByUser_UserId(Long userId);
}
