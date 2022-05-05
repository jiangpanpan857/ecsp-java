package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.Methodology;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Methodology entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MethodologyRepository extends JpaRepository<Methodology, Long> {}
