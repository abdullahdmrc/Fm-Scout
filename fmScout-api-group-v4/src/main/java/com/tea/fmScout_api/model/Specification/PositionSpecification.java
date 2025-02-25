package com.tea.fmScout_api.model.Specification;

import com.tea.fmScout_api.model.FootballPlayer;
import com.tea.fmScout_api.model.Position;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public class PositionSpecification implements Specification<FootballPlayer> {
    private List<Integer> positionIds; // Pozisyon ID'leri

    public PositionSpecification(List<Integer> positionIds) {
        this.positionIds = positionIds;
    }

    @Override
    public Predicate toPredicate(Root<FootballPlayer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (positionIds == null || positionIds.isEmpty()) {
            return criteriaBuilder.conjunction(); // Boş liste ise, her şeyi döndür
        }

        Join<FootballPlayer, Position> positions = root.join("positions");
        return positions.get("id").in(positionIds);
    }
}