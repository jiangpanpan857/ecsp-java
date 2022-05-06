package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.SysCompany;
import com.ruowei.ecsp.domain.enumeration.CompanyStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the SysCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysCompanyRepository extends JpaRepository<SysCompany, Long> {
    /**
     * @param uniCreditCode
     * @return
     * @apiNote author: czz; can't share uniCreditCode with any company when add.
     */
    Boolean existsByUniCreditCodeAndStatus(@NotNull String uniCreditCode, CompanyStatusType status);

    /**
     * @param uniCreditCode
     * @param id
     * @return
     * @apiNote author: czz; can't share uniCreditCode with any other company when edit.
     */
    Boolean existsByUniCreditCodeAndIdNotAndStatus(@NotNull String uniCreditCode, Long id, CompanyStatusType status);

    Optional<SysCompany> findByUniCreditCodeAndStatus(@NotNull String uniCreditCode, CompanyStatusType status);

    Optional<SysCompany> findByIdAndStatus(Long id, CompanyStatusType status);

    Optional<SysCompany> findByUniCreditCode(String uniCreditCode);

    List<SysCompany> findAllByCompanyNameLike(String companyName);
}
