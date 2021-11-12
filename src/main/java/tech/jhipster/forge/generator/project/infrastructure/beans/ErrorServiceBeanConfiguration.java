package tech.jhipster.forge.generator.project.infrastructure.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.forge.generator.project.domain.service.server.javatool.error.ErrorDomainService;
import tech.jhipster.forge.generator.project.domain.service.server.javatool.error.ErrorRepository;
import tech.jhipster.forge.generator.project.domain.service.server.javatool.error.ErrorService;

@Configuration
public class ErrorServiceBeanConfiguration {

  private final ErrorRepository errorRepository;

  public ErrorServiceBeanConfiguration(ErrorRepository errorRepository) {
    this.errorRepository = errorRepository;
  }

  @Bean
  public ErrorService errorService() {
    return new ErrorDomainService(errorRepository);
  }
}
