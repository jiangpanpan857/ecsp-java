package com.ruowei.ecsp.web.rest.qm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SinUserQM {

    /**
     * 城市
     */
    private String cityName;

    /**
     * 机构名称
     */
    private String organizationName;

}
