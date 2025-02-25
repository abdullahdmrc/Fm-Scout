package com.tea.fmScout_api.model.Specification;

import com.tea.fmScout_api.model.FootballPlayer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class MarketValueSpecification implements Specification<FootballPlayer> {
    private int minMarketValue;
    private int maxMarketValue;

    public MarketValueSpecification(int minMarketValue, int maxMarketValue)
    {
        this.minMarketValue = minMarketValue;
        this.maxMarketValue = maxMarketValue;
    }

    @Override
    public Predicate toPredicate(Root<FootballPlayer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.between(root.get("marketValue"), minMarketValue, maxMarketValue);
    }
}
