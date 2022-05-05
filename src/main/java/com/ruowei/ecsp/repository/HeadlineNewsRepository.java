package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.HeadlineNews;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HeadlineNews entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeadlineNewsRepository extends JpaRepository<HeadlineNews, Long> {}
