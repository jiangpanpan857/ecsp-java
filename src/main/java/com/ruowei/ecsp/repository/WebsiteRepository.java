package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.web.rest.dto.WebsiteDownListDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Website entity.
 */
@Repository
public interface WebsiteRepository extends JpaRepository<Website, Long>, QuerydslPredicateExecutor<Website> {
    boolean existsByDomain(String domain);

    boolean existsByDomainAndIdNot(String domain, Long id);

    @Query("select new com.ruowei.ecsp.web.rest.dto.WebsiteDownListDTO(w.id, w.name) from Website w WHERE  1 = 1 order by  w.addTime desc ")
    List<WebsiteDownListDTO> getWebsiteDownList();
}
