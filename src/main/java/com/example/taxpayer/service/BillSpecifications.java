package com.example.taxpayer.service;

import com.example.taxpayer.api.entity.BillEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BillSpecifications {
    public static Specification<BillEntity> filterBills(Long userId, Double minAmount, Double maxAmount, String dueDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (userId != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("user").get("id"), userId));
            }
            if (minAmount != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount));
            }
            if (maxAmount != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount));
            }
            if (dueDate != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dueDate"), dueDate));
            }
            return predicate;
        };
    }
}
