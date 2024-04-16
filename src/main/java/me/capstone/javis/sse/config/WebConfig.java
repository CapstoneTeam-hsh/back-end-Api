package me.capstone.javis.sse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static final String ALLOW_METHOD_NAMES = "GET,HEAD,POST,DELETE,TRACE,OPTIONS,PATCH";

    @Override
    public void addCorsMappings(final CorsRegistry registry){
        registry.addMapping("/**")
                .allowedMethods(ALLOW_METHOD_NAMES.split(","));
    }
}
