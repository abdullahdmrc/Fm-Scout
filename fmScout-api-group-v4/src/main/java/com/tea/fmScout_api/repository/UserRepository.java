package com.tea.fmScout_api.repository;

import com.tea.fmScout_api.model.FootballPlayer;
import com.tea.fmScout_api.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    Optional<User> findByUserName(String userName);

    List<User> findAll(Sort sort);

    boolean existsByUserName(String userName);

    Optional<User> findIdByUserName(String userName);

    List<User> findByIsAdminFalse();
}