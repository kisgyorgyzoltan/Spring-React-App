package edu.bbte.idde.kzim2149.specification;

import edu.bbte.idde.kzim2149.model.PcPart;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class PcPartSpecification {
    public static Specification<PcPart> filterByThreeCriteria(String producer, String type, Integer maxPrice, Integer minPrice) {
        return (Root<PcPart> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (producer != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("producer"), "%" + producer + "%"));
            }

            if (type != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("type"), "%" + type + "%"));
            }

            if (maxPrice != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (minPrice != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            return predicate;
        };
    }

}
