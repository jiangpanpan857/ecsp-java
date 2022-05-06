package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.User;
import com.ruowei.ecsp.domain.enumeration.UserStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    Optional<User> findOneByLogin(@NotNull String login);

    Optional<User> findOneByLoginAndStatusNot(@NotNull String login, @NotNull UserStatusType status);

    Boolean existsByLoginAndCompanyIdNotAndStatusNot(String login, Long companyId, UserStatusType status);

    List<User> findAllByIdInAndStatusNot(Collection<Long> id, @NotNull UserStatusType status);

    Optional<User> findFirstByLoginAndIdNotAndStatusNot(@NotNull String login, Long id, @NotNull UserStatusType status);

    Optional<User> findByIdAndStatus(Long id, @NotNull UserStatusType status);

    Optional<User> findByIdAndStatusNot(Long id, @NotNull UserStatusType status);

    Optional<User> findOneByLoginAndStatus(@NotNull String login, @NotNull UserStatusType status);

    Optional<User> findByEnterpriseId(Long enterpriseId);

    Optional<User> findByCompanyId(Long companyId);

    Optional<User> findByThirdPartyId(Long thirdPartyId);

    List<User> findAllByNickNameLike(String nickName);

    List<User> findAllByNickNameContains(String nickName);

    boolean existsByLoginAndStatus(String login, UserStatusType status);
}
