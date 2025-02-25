package com.tea.fmScout_api.repository;

import com.tea.fmScout_api.model.FreeTransfersView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeTransferRepository extends JpaRepository<FreeTransfersView, Long> {
}
