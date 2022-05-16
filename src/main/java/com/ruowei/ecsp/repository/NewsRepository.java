package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.News;
import com.ruowei.ecsp.web.rest.dto.NewsProjectDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the News entity.
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Long>, QuerydslPredicateExecutor<News> {

    Boolean existsByTitleAndWebsiteId(String title, Long websiteId);

    Boolean existsByTitleAndWebsiteIdAndIdNot(String title, Long websiteId, Long id);

    @Query(value = "SELECT new com.ruowei.ecsp.web.rest.dto.NewsProjectDTO(n.type, count(n.type)) FROM News n WHERE ((?1 is null) or (n.title like ?1 )) and n.status = '已发布' and n.websiteId = ?2 GROUP BY n.type")
    List<NewsProjectDTO> searchByTitle(String title, Long websiteId);
}
