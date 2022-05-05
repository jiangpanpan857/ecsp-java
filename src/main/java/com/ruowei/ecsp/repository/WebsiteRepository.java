package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.Website;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Website entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WebsiteRepository extends JpaRepository<Website, Long> {}
