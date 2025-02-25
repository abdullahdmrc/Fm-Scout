package com.tea.fmScout_api.model.Specification;

import com.tea.fmScout_api.model.FootballPlayer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class LeagueSpecification implements Specification<FootballPlayer> {
    private Long leagueId;

    public LeagueSpecification(Long leagueId)
    {
        this.leagueId = leagueId;
    }

    @Override
    public Predicate toPredicate(Root<FootballPlayer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (leagueId == null) {
            return criteriaBuilder.conjunction();
        }

        return criteriaBuilder.equal(root.get("club").get("league").get("leagueId"),leagueId);
    }
}
