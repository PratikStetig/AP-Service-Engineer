package com.asianpaints.apse.service_engineer.specification;

import com.asianpaints.apse.service_engineer.constants.UserRole;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteFilter;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteStatus;
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


            String userRole = filter.getRoleName().trim();
            if (UserRole.ADMIN.equalsIgnoreCase(userRole)) {
                // Admin: Can see all inspections in all statuses
                logger.info("Admin role: No additional predicates added for status");
            } else if (UserRole.APPROVER.equalsIgnoreCase(userRole)) {
                // Approver: Can see inspections in status (pending, approved, rejected)
                predicates.add(root.get("status").in(
                        InspectionSiteStatus.Pending,
                        InspectionSiteStatus.Approved,
                        InspectionSiteStatus.Rejected
                ));
                logger.info("Approver role: Added status predicates for pending, approved, and rejected");
            } else if (UserRole.SERVICE_ENGINEER.equalsIgnoreCase(userRole)) {
                // Service Engineer: Can see all approved reports
                Predicate approvedReports = criteriaBuilder.equal(root.get("status"), InspectionSiteStatus.Approved);

                // Service Engineer: Can see rejected, pending, draft, and approved reports if conducted by self
                Predicate selfReports = criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("conductedBy"), filter.getConductedBy()),
                        root.get("status").in(
                                InspectionSiteStatus.Rejected,
                                InspectionSiteStatus.Pending,
                                InspectionSiteStatus.Draft,
                                InspectionSiteStatus.Approved
                        )
                );

                predicates.add(criteriaBuilder.or(approvedReports, selfReports));
                logger.info("Service-Engineer role: Added predicates for approved status and self-conducted pending/rejected statuses");
            }

            logger.info("Total predicates: {}", predicates.size());

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}