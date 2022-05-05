package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.EcoUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EcoUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcoUserRepository extends JpaRepository<EcoUser, Long> {
    Optional<EcoUser> findOneByLogin(String login);
}
