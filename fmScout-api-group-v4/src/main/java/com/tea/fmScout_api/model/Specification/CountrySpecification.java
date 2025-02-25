package com.tea.fmScout_api.model.Specification;

import com.tea.fmScout_api.model.FootballPlayer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CountrySpecification implements Specification<FootballPlayer> {
    private Long countryId;

    public CountrySpecification(Long countryId) {
        this.countryId = countryId;
    }

    @Override
    public Predicate toPredicate(Root<FootballPlayer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (countryId == null) {
            return criteriaBuilder.conjunction();
        }

        return criteriaBuilder.equal(root.get("country").get("countryId"),countryId);
    }
}
