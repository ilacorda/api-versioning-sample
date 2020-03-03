package com.tui.apiversioningsample.ws.configuration;

import com.tui.apiversioningsample.dto.FooV1DTO;
import com.tui.apiversioningsample.dto.FooV2DTO;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Value("${info.application_name}")
  private String applicationName;
  @Value("${info.description}")
  private String description;
  @Value("${info.project_version}")
  private String version;

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info().title(applicationName).description(description).version(version));
  }

  @Bean
  public GroupedOpenApi v1() {
    return GroupedOpenApi.builder().setGroup(FooV1DTO.MEDIA_TYPE).packagesToScan("com.tui.apiversioningsample.ws.controller.v1")
        .build();
  }

  @Bean
  public GroupedOpenApi v2() {
    return GroupedOpenApi.builder().setGroup(FooV2DTO.MEDIA_TYPE).packagesToScan("com.tui.apiversioningsample.ws.controller.v2")
        .build();
  }
}
