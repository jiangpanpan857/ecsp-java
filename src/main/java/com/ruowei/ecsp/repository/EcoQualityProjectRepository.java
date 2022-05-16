package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.EcoQualityProject;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the EcoQualityProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcoQualityProjectRepository extends JpaRepository<EcoQualityProject, Long>, QuerydslPredicateExecutor<EcoQualityProject> {
    Boolean existsByNameAndWebsiteId(String name, Long websiteId);

    Boolean existsByNameAndWebsiteIdAndIdNot(String name, Long websiteId, Long id);

    @Query(value = "select distinct e.type from EcoQualityProject e where e.websiteId = ?1")
    List<String> getTypes(Long websiteId);

    @Query(value = "select count(e.id) from EcoQualityProject e where e.websiteId = ?2 and e.status = '已发布' and ((?1 is null) or (e.name like ?1))")
    Long searchByName(String name, Long websiteId);
}
