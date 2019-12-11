package com.tui.apiversioningsample.ws.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author abujosa
 * @version 1.0
 * @see
 * @since JDK1.8
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
  public static final int DEFAULT_POOL_SIZE = 10;
  public static final int DEFAULT_QUEUE_SIZE = 100;

  /**
   * @return
   */
  @Bean
  public Filter shallowEtagHeaderFilter() {
    return new ShallowEtagHeaderFilter();
  }

  @Override
  public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(DEFAULT_POOL_SIZE);
    executor.setMaxPoolSize(DEFAULT_POOL_SIZE);
    executor.setQueueCapacity(DEFAULT_QUEUE_SIZE);
    executor.setThreadNamePrefix("AsyncTaskExecutor-");
    executor.initialize();
    configurer.setTaskExecutor(executor);
  }
}
