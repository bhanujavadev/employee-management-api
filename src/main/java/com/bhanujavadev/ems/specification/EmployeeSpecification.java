package com.bhanujavadev.ems.specification;

import com.bhanujavadev.ems.dto.request.EmployeeSearchRequest;
import com.bhanujavadev.ems.entity.Employee;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    private EmployeeSpecification() {
    }

    public static Specification<Employee> search(EmployeeSearchRequest request) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Keyword Search
            if (request.getKeyword() != null &&
                    !request.getKeyword().isBlank()) {

                String keyword =
                        "%" + request.getKeyword().toLowerCase() + "%";

                predicates.add(

                        cb.or(

                                cb.like(
                                        cb.lower(root.get("firstName")),
                                        keyword),

                                cb.like(
                                        cb.lower(root.get("lastName")),
                                        keyword),

                                cb.like(
                                        cb.lower(root.get("email")),
                                        keyword),

                                cb.like(
                                        cb.lower(root.get("employeeCode")),
                                        keyword)
                        )
                );
            }

            // Department Filter
            if (request.getDepartmentId() != null) {

                predicates.add(

                        cb.equal(
                                root.get("department").get("id"),
                                request.getDepartmentId()
                        )
                );
            }

            // Designation Filter
            if (request.getDesignationId() != null) {

                predicates.add(

                        cb.equal(
                                root.get("designation").get("id"),
                                request.getDesignationId()
                        )
                );
            }

            // Active Filter
            if (request.getActive() != null) {

                predicates.add(

                        cb.equal(
                                root.get("active"),
                                request.getActive()
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}