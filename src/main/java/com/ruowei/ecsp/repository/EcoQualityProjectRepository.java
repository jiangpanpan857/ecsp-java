package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.EcoQualityProject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EcoQualityProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcoQualityProjectRepository extends JpaRepository<EcoQualityProject, Long> {}
