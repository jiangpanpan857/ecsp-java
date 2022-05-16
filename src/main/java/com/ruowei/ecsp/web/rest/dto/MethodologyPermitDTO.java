package com.ruowei.ecsp.web.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodologyPermitDTO {
    /**
     * 方法学类型
     */
    @Schema(description = "方法学类型")
    private String type;

    /**
     * 类型下方法学列表
     */
    @Schema(description = "类型下方法学列表")
    private List<MethodTemp> methodTempList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MethodTemp {

        private Long id;

        /**
         * 类型
         */
        @Schema(description = "类型")
        private String type;

        /**
         * 名称
         */
        @Schema(description = "名称")
        private String name;

        /**
         * 简介
         */
        @Schema(description = "简介")
        private String introduction;

        /**
         * 图片
         */
        @Schema(description = "图片")
        private String image;

        /**
         * 方法学文件
         */
        @Schema(description = "方法学文件")
        private String attachment;
    }

}
