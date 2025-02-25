package com.tea.fmScout_api.model.Specification;

import com.tea.fmScout_api.model.FootballPlayer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class PaSpecification implements Specification<FootballPlayer> {
    private int minPa;
    private int maxPa;

    public PaSpecification(int minPa, int maxPa)
    {
        this.minPa = minPa;
        this.maxPa = maxPa;
    }

    @Override
    public Predicate toPredicate(Root<FootballPlayer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.between(root.get("pa"), minPa, maxPa);
    }
}
