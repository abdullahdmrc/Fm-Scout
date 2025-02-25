package com.tea.fmScout_api.service;

import com.tea.fmScout_api.model.Position;
import com.tea.fmScout_api.repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository)
    {
        this.positionRepository = positionRepository;
    }

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    public Optional<Position> getPositionById(Long positionId) {
        return positionRepository.findById(positionId);
    }
}
