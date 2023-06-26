package com.project.config;
import org.ehcache.config.ResourceType;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.project.model.Student;

import javax.cache.CacheManager;
import javax.cache.Caching;
//import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.io.File;
import java.time.Duration;
import java.util.Collections;
import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.ehcache.EhCacheCacheManager;
//import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager ehCacheManager() {
        

        CachingProvider provider = Caching.getCachingProvider(EhcacheCachingProvider.class.getName());
        CacheManager cacheManager = provider.getCacheManager();
        

        org.ehcache.config.CacheConfiguration<Integer, Student> configuration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        Integer.class,
                        Student.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(1000, EntryUnit.ENTRIES) // In-memory heap size
                               
                                .build())
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(20)))
                        .build();

        javax.cache.configuration.Configuration<Integer, Student> studentConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(configuration);

        cacheManager.createCache("students", studentConfiguration);
        return cacheManager;
    }

    @Bean
    public org.springframework.cache.CacheManager cacheManager() {
        JCacheCacheManager jCacheCacheManager = new JCacheCacheManager(ehCacheManager());
        return jCacheCacheManager;
    }
}
	
	