package com.wz.yisitest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author：wzxgd
 * @date：10/10/2019 --------------
 */

@SpringBootApplication
public class Start  extends WebMvcConfigurationSupport {
    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);

    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"static/");
        super.addResourceHandlers(registry);
    }
}
