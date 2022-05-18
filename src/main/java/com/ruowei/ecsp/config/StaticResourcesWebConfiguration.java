package com.ruowei.ecsp.config;

import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.config.JHipsterProperties;

@Configuration
@Profile({ JHipsterConstants.SPRING_PROFILE_PRODUCTION })
public class StaticResourcesWebConfiguration implements WebMvcConfigurer {

    protected static final String[] RESOURCE_LOCATIONS = new String[] { "file:./public/", "file:./public/static/" };
    protected static final String[] RESOURCE_PATHS = new String[] { "/**.js", "/**.css", "/**.svg", "/**.png", "/**.ico", "/static/**" };

    private final JHipsterProperties jhipsterProperties;
    private final ApplicationProperties applicationProperties;

    public StaticResourcesWebConfiguration(JHipsterProperties jHipsterProperties, ApplicationProperties applicationProperties) {
        this.jhipsterProperties = jHipsterProperties;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration resourceHandlerRegistration = appendResourceHandler(registry);
        initializeResourceHandler(resourceHandlerRegistration);
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + applicationProperties.getUploadPath());
        registry.addResourceHandler("/sin/upload/**").addResourceLocations("file:" + "/data/projects/cnsp/upload/" );
    }

    protected ResourceHandlerRegistration appendResourceHandler(ResourceHandlerRegistry registry) {
        return registry.addResourceHandler(RESOURCE_PATHS);
    }

    protected void initializeResourceHandler(ResourceHandlerRegistration resourceHandlerRegistration) {
        resourceHandlerRegistration.addResourceLocations(RESOURCE_LOCATIONS).setCacheControl(getCacheControl());
    }

    protected CacheControl getCacheControl() {
        return CacheControl.maxAge(getJHipsterHttpCacheProperty(), TimeUnit.DAYS).cachePublic();
    }

    private int getJHipsterHttpCacheProperty() {
        return jhipsterProperties.getHttp().getCache().getTimeToLiveInDays();
    }
}
