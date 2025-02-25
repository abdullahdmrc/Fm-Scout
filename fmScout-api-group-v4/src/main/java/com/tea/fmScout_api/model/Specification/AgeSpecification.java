package com.tea.fmScout_api.model.Specification;

import com.tea.fmScout_api.model.FootballPlayer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class AgeSpecification implements Specification<FootballPlayer> {
    private int minAge;
    private int maxAge;

    public AgeSpecification(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    @Override
    public Predicate toPredicate(Root<FootballPlayer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.between(root.get("age"), minAge, maxAge);
    }
}
