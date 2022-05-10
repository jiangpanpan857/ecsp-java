package com.ruowei.ecsp.web.rest.mapper;

import com.ruowei.ecsp.domain.EcoUser;
import com.ruowei.ecsp.util.mapper.EntityMapper;
import com.ruowei.ecsp.web.rest.dto.EcoUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EcoUserDTOMapper extends EntityMapper<EcoUserDTO, EcoUser> {
}
