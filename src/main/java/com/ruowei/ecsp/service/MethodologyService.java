package com.ruowei.ecsp.service;

import com.querydsl.core.BooleanBuilder;
import com.ruowei.ecsp.domain.Methodology;
import com.ruowei.ecsp.domain.QMethodology;
import com.ruowei.ecsp.repository.MethodologyRepository;
import com.ruowei.ecsp.util.AssertUtil;
import com.ruowei.ecsp.util.PageUtil;
import com.ruowei.ecsp.web.rest.qm.MethodologyQM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
public class MethodologyService {
    private final MethodologyRepository methodologyRepository;

    private QMethodology qM = QMethodology.methodology;

    public MethodologyService(MethodologyRepository methodologyRepository) {
        this.methodologyRepository = methodologyRepository;
    }


    public void createMethodology(Methodology methodology) {
        AssertUtil.notNullThrow(methodology.getId(), "新增失败", "新增时ID只能为空");
        assertMethodology(methodology);
        methodology.setAddTime(ZonedDateTime.now());
        methodologyRepository.save(methodology);
    }

    public void updateMethodology(Methodology methodology) {
        AssertUtil.nullThrow(methodology.getId(), "修改失败", "修改时ID不能为空");
        assertMethodology(methodology);
        methodologyRepository.save(methodology);
    }

    public ResponseEntity<List<Methodology>> searchAll(MethodologyQM qm, Pageable pageable) {
        BooleanBuilder builder = new OptionalBooleanBuilder()
            .notEmptyAnd(qM.name::contains, qm.getName())
            .notEmptyAnd(qM.type::eq, qm.getType())
            .build();
        Page<Methodology> page = methodologyRepository.findAll(builder, pageable);
        return PageUtil.pageReturn(page);
    }

    private void assertMethodology(Methodology methodology) {
        if (methodology.getId() == null) {
            AssertUtil.thenThrow(methodologyRepository.existsByName(methodology.getName()), "新增方法学失败！", "该方法论已存在");
        } else {
            AssertUtil.thenThrow(methodologyRepository.existsByNameAndIdNot(methodology.getName(), methodology.getId()), "修改方法学失败！", "该方法论已存在");
        }
    }
}
