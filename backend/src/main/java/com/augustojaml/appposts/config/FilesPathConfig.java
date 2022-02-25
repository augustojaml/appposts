package com.augustojaml.appposts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilesPathConfig {

  private String filePath = "/uploads/**";
  private String pathPatterns = "./uploads/";

  @Bean
  public WebMvcConfigurer webMvcConfigurerAdapter() {

    return new WebMvcConfigurer() {
      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler(filePath)
            .addResourceLocations("file:" + pathPatterns);
      }

      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
      }
    };
  }
}
