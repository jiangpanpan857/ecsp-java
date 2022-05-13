package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.EcoResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the EcoResource entity.
 */
@Repository
public interface EcoResourceRepository extends JpaRepository<EcoResource, Long>, QuerydslPredicateExecutor<EcoResource> {
    Boolean existsByTitleAndWebsiteId(String title, Long websiteId);

    Boolean existsByTitleAndWebsiteIdAndIdNot(String title, Long websiteId, Long id);

    List<EcoResource> findAllByIdIn(List<Long> ids);

    @Query(value = "SELECT s.sequence FROM eco_resource s WHERE s.sequence is not NULL ORDER BY s.sequence DESC LIMIT 1;", nativeQuery = true)
    Integer findMaxSequence();
}
