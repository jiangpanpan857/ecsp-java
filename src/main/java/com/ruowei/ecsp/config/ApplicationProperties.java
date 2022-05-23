package com.ruowei.ecsp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Ecsp.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String uploadPath;
    private String ecspUploadPath;

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getEcspUploadPath() {
        return ecspUploadPath;
    }

    public void setEcspUploadPath(String ecspUploadPath) {
        this.ecspUploadPath = ecspUploadPath;
    }
}
