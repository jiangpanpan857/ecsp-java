package com.ruowei.ecsp.web.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SinUserDTO {

    /**
     * userId
     */
    @Schema(description = "userId")
    private Long id;

    /**
     * userName
     */
    @Schema(description = "userName")
    private String login;
}
