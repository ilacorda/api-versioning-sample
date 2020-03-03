package com.tui.architecture.archetype.ws.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Value("${info.application_name}")
  private String serviceName;
  @Value("${info.description:}")
  private String description;
  @Value("${info.maven_version}")
  private String version;

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info().title(serviceName).description(description).version(version));
  }
}
