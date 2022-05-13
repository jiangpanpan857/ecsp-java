package com.ruowei.ecsp.web.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebsiteDownListDTO {
    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 网站名称
     */
    @Schema(description = "网站名称")
    private String name;
}
