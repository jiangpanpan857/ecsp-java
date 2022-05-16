package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.HeadlineNews;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the HeadlineNews entity.
 */
@Repository
public interface HeadlineNewsRepository extends JpaRepository<HeadlineNews, Long>, QuerydslPredicateExecutor<HeadlineNews> {
    Boolean existsByTitleAndWebsiteId(String title, Long websiteId);

    Boolean existsByTitleAndWebsiteIdAndIdNot(String title, Long websiteId, Long id);

    @Query(value = "SELECT s.sequence FROM eco_headline_news s WHERE s.sequence is not NULL ORDER BY s.sequence DESC LIMIT 1;", nativeQuery = true)
    Integer findMaxSequence();

    List<HeadlineNews> findAllByIdIn(List<Long> ids);
}
