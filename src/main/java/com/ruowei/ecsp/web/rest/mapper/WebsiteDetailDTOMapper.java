package com.ruowei.ecsp.web.rest.mapper;

import com.ruowei.ecsp.domain.Website;
import com.ruowei.ecsp.util.mapper.EntityMapper;
import com.ruowei.ecsp.web.rest.dto.WebsiteDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WebsiteDetailDTOMapper extends EntityMapper<WebsiteDetailDTO, Website> {
}
