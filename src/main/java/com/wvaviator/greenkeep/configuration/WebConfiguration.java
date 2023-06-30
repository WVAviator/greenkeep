package com.wvaviator.greenkeep.configuration;

import com.wvaviator.greenkeep.security.AuthenticatedUserArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Slf4j
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        log.info("Adding argument resolver for authenticated user.");
        resolvers.add(new AuthenticatedUserArgumentResolver());
    }
}
