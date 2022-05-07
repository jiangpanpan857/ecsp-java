package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.Methodology;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Methodology entity.
 */
@Repository
public interface MethodologyRepository extends JpaRepository<Methodology, Long>, QuerydslPredicateExecutor<Methodology> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
