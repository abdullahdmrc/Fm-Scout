package com.tea.fmScout_api.model.Specification;

import com.tea.fmScout_api.model.FootballPlayer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ClubSpecification implements Specification<FootballPlayer> {
    private Long clubId; // Ülke ID'si

    public ClubSpecification(Long clubId) {
        this.clubId = clubId;
    }

    @Override
    public Predicate toPredicate(Root<FootballPlayer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (clubId == null) {
            return criteriaBuilder.conjunction();
        }

        return criteriaBuilder.equal(root.get("club").get("id"), clubId);
    }
}
