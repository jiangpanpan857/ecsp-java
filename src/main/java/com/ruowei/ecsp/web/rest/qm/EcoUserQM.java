package com.ruowei.ecsp.web.rest.qm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcoUserQM {
    private String login;

    private String websiteName;

    private String realName;
}
