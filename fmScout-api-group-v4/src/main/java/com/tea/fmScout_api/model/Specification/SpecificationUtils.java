package com.tea.fmScout_api.model.Specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SpecificationUtils {

    public static <T> Specification<T> combine(List<Specification<T>> specifications) {
        return (root, query, criteriaBuilder) -> {
            if (specifications == null || specifications.isEmpty()) {
                return criteriaBuilder.conjunction(); // Boş liste ise her şeyi döndür
            }
            Predicate[] predicates = specifications.stream()
                    .map(spec -> spec.toPredicate(root, query, criteriaBuilder))
                    .toArray(Predicate[]::new);

            return criteriaBuilder.and(predicates);
        };
    }
}