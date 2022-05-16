package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.Methodology;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Methodology entity.
 */
@Repository
public interface MethodologyRepository extends JpaRepository<Methodology, Long>, QuerydslPredicateExecutor<Methodology> {
    Boolean existsByName(String name);

    Boolean existsByNameAndIdNot(String name, Long id);

    List<Methodology> findByIdIn(List<Long> methodologyIds);
}
