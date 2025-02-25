package com.tea.fmScout_api.repository;

import com.tea.fmScout_api.model.WonderkidsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WonderkidRepository extends JpaRepository<WonderkidsView, Long> {
}
