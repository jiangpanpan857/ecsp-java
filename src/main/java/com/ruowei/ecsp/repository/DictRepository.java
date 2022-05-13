package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.Dict;
import com.ruowei.ecsp.web.rest.dto.DictDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Dict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DictRepository extends JpaRepository<Dict, Long> {
    List<DictDTO> findAllByCatagoryCodeAndDisabledIsFalseOrderBySortNo(String catagory);

    /**
     * 根据分类编码和字典编码获取字典名称
     * @param catagoryCode
     * @param dictCode
     * @return
     */
    Optional<Dict> findOneByCatagoryCodeAndDictCodeAndDisabledIsFalse(@NotNull String catagoryCode, @NotNull String dictCode);
}
