package com.tea.fmScout_api.model.Specification;

import com.tea.fmScout_api.model.FootballPlayer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CaSpecification implements Specification<FootballPlayer> {
    private int minCa;
    private int maxCa;

    public CaSpecification(int minCa, int maxCa)
    {
        this.minCa = minCa;
        this.maxCa = maxCa;
    }

    @Override
    public Predicate toPredicate(Root<FootballPlayer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.between(root.get("ca"), minCa, maxCa);
    }
}
