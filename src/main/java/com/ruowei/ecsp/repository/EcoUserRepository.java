package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.EcoUser;
import java.util.Optional;

import com.ruowei.ecsp.web.rest.UserJWTController;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EcoUser entity.
 */
@Repository
public interface EcoUserRepository extends JpaRepository<EcoUser, Long> {
    Optional<EcoUser> findOneByLogin(String login);

    boolean existsByLoginAndIdNot(String login, Long id);

    boolean existsByLoginAndWebsiteId(String login, Long websiteId);

    EcoUser findByLogin(String login);


}
