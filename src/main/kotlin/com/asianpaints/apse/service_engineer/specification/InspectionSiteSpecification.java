package com.asianpaints.apse.service_engineer.specification;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InspectionSiteSpecification {

    private static final Logger logger = LoggerFactory.getLogger(InspectionSiteSpecification.class);

    public static Specification<InspectionSite> getFilteredInspectionSites(InspectionSiteFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Boolean.TRUE.equals(!filter.getAdmin()) && filter.getConductedBy() != null) {
                predicates.add(criteriaBuilder.equal(root.get("conductedBy"), filter.getConductedBy()));
                logger.info("Added fromDate predicate: {}", filter.getFromDate());
            }

            if (filter.getFromDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("inspectionDate"), filter.getFromDate()));
                logger.info("Added fromDate predicate: {}", filter.getFromDate());
            }

            if (filter.getToDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("inspectionDate"), filter.getToDate()));
                logger.info("Added toDate predicate: {}", filter.getToDate());
            }

            if (filter.getSiteId() != null && !filter.getSiteId().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("siteId")), "%" + filter.getSiteId().toLowerCase() + "%"));
                logger.info("Added siteId predicate: {}", filter.getSiteId());
            }

            if (filter.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), filter.getStatus()));
                logger.info("Added status predicate: {}", filter.getStatus());
            }

            if (filter.getReportName() != null && !filter.getReportName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("reportName")), "%" + filter.getReportName().toLowerCase() + "%"));
                logger.info("Added reportName predicate: {}", filter.getReportName());
            }

            predicates.add(criteriaBuilder.isFalse(root.get("deleted")));
            logger.info("Added deleted = false predicate");

            logger.info("Total predicates: {}", predicates.size());

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}