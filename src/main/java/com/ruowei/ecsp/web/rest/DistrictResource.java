package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.service.DistrictService;
import com.ruowei.ecsp.web.rest.dto.DistrictDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/district")
@Tag(name = "省市区管理")
public class DistrictResource {

    private final Logger log = LoggerFactory.getLogger(DistrictResource.class);
    private final DistrictService districtService;

    public DistrictResource(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping("/permit")
    @Operation(summary = "获取省市区下拉列表接口", description = "作者：林宏栋")
    @Cacheable(cacheNames = "com.ruowei.ecsp.domain.District")
    public ResponseEntity<List<DistrictDTO>> getProvinceCityDistricts() {
        List<DistrictDTO> dtoList = districtService.getDistrictTree();
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/permit/only-province")
    @Operation(summary = "获取省下拉列表接口", description = "作者：林宏栋")
    public ResponseEntity<List<DistrictDTO>> getProvinceDistricts() {
        List<DistrictDTO> dtoList = districtService.getProvince();
        return ResponseEntity.ok().body(dtoList);
    }
}
