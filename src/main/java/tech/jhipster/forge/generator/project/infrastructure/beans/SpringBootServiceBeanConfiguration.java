package tech.jhipster.forge.generator.project.infrastructure.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.core.SpringBootDomainService;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.core.SpringBootRepository;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.core.SpringBootService;

@Configuration
public class SpringBootServiceBeanConfiguration {

  private final SpringBootRepository springBootRepository;

  public SpringBootServiceBeanConfiguration(SpringBootRepository springBootRepository) {
    this.springBootRepository = springBootRepository;
  }

  @Bean
  public SpringBootService springBootService() {
    return new SpringBootDomainService(springBootRepository);
  }
}
