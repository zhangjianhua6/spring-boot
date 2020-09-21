package com.jason.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "jason.gateway.dynamicRoute", name = "enabled", havingValue = "true")
public class DynamicRouteConfig {


    /**
     * Nacos实现方式
     */
    @Configuration
    @ConditionalOnProperty(prefix = "jason.gateway.dynamicRoute", name = "dataType", havingValue = "properties", matchIfMissing = true)
    public class PropertiesRoute {
        @Autowired
        private GatewayProperties gatewayProperties;

        @Bean
        public PropertiesRouteDefinitionLocator nacosRouteDefinitionRepository() {
            return new PropertiesRouteDefinitionLocator(gatewayProperties);
        }
    }

}
