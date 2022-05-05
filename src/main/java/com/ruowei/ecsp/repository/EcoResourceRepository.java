package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.EcoResource;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EcoResource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcoResourceRepository extends JpaRepository<EcoResource, Long> {}
