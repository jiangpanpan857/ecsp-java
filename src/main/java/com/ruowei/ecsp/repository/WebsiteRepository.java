package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.web.rest.dto.WebsiteDownListDTO;
import com.ruowei.ecsp.web.rest.dto.WebsiteVisDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Website entity.
 */
@Repository
public interface WebsiteRepository extends JpaRepository<Website, Long>, QuerydslPredicateExecutor<Website> {
    boolean existsByDomain(String domain);

    boolean existsByDomainAndIdNot(String domain, Long id);

    boolean existsByMethodologyIdsContains(@NotNull String idStr);

    @Query(value = "select new com.ruowei.ecsp.web.rest.dto.WebsiteDownListDTO(w.id, w.name) from Website w WHERE  1 = 1 order by  w.addTime desc ")
    List<WebsiteDownListDTO> getWebsiteDownList();

    @Query(value = "select new com.ruowei.ecsp.web.rest.dto.WebsiteVisDTO(w.id, w.name, w.domain, w.organizationName, w.cityId, w.cityName," +
        " w.websiteContact, w.websiteContactNumber, w.email, w.logo, w.headerImg, w.businessNumber, w.address, w.methodologyIds) from Website w WHERE  w.domain = ?1")
    WebsiteVisDTO getWebsiteVis(String domain);

    Optional<Website> findByDomain(String domain);

    @Query(value = "select w.id from Website w where w.domain = ?1")
    Long getIdByDomain(String domain);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    List<Website> findAllByCarbonLibraAccountIn(List<String> carbonLibraAccounts);
}
