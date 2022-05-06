package com.ruowei.ecsp.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.ruowei.ecsp.domain.Website.class.getName());
            createCache(cm, com.ruowei.ecsp.domain.Website.class.getName() + ".ecoUsers");
            createCache(cm, com.ruowei.ecsp.domain.Website.class.getName() + ".headlineNews");
            createCache(cm, com.ruowei.ecsp.domain.Website.class.getName() + ".news");
            createCache(cm, com.ruowei.ecsp.domain.Website.class.getName() + ".ecoResources");
            createCache(cm, com.ruowei.ecsp.domain.Website.class.getName() + ".ecoQualityProjects");
            createCache(cm, com.ruowei.ecsp.domain.EcoUser.class.getName());
            createCache(cm, com.ruowei.ecsp.domain.Methodology.class.getName());
            createCache(cm, com.ruowei.ecsp.domain.Methodology.class.getName() + ".websites");
            createCache(cm, com.ruowei.ecsp.domain.HeadlineNews.class.getName());
            createCache(cm, com.ruowei.ecsp.domain.News.class.getName());
            createCache(cm, com.ruowei.ecsp.domain.EcoResource.class.getName());
            createCache(cm, com.ruowei.ecsp.domain.EcoQualityProject.class.getName());
//            // col - entity cache
//            createCache(cm, com.ruowei.ecsp.domain.ForestData.class.getName());
//            createCache(cm, com.ruowei.ecsp.domain.CarbonTrade.class.getName());
//            // jhipster-needle-ehcache-add-entry
//            createCache(cm, com.ruowei.ecsp.repository.ForestDataRepository.FORESTDATA_ALL_PROVINCE_STORAGE_BY_YEAR);
//            createCache(cm, com.ruowei.ecsp.repository.ForestDataRepository.FORESTDATA_ALL_PROVINCE_AREA_INCREMENT_BY_YEAR);
//            createCache(cm, com.ruowei.ecsp.repository.ForestDataRepository.FORESTDATA_ALL_CITY_STORAGE_BY_PROVINCE_AND_YEAR);
//            createCache(cm, com.ruowei.ecsp.repository.ForestDataRepository.FORESTDATA_ALL_CITY_AREA_INCREMENT_BY_PROVINCE_AND_YEAR);
//            createCache(cm, com.ruowei.ecsp.repository.ForestDataRepository.FORESTDATA_ALL_PROVINCE_STORAGE_RANKING_BY_YEAR);
//            createCache(cm, com.ruowei.ecsp.repository.ForestDataRepository.FORESTDATA_ALL_PROVINCE_AREA_INCREMENT_RANKING_BY_YEAR);
//            createCache(cm, com.ruowei.ecsp.repository.ForestDataRepository.FORESTDATA_ALL_CITY_STORAGE_RANKING_BY_PROVINCE_AND_YEAR);
//            createCache(cm, com.ruowei.ecsp.repository.ForestDataRepository.FORESTDATA_ALL_CITY_AREA_INCREMENT_RANKING_BY_PROVINCE_AND_YEAR);
//            createCache(cm, com.ruowei.ecsp.repository.ForestDataRepository.FORESTDATA_ALL_YEAR_STORAGE_BY_PROVINCE);
//            createCache(cm, com.ruowei.ecsp.repository.ForestDataRepository.FORESTDATA_ALL_YEAR_AREA_INCREMENT_BY_PROVINCE);
//            createCache(cm, com.ruowei.ecsp.repository.ForestDataRepository.FORESTDATA_ALL_YEAR_STORAGE_BY_CITY);
//            createCache(cm, com.ruowei.ecsp.repository.ForestDataRepository.FORESTDATA_ALL_YEAR_AREA_INCREMENT_BY_CITY);
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
