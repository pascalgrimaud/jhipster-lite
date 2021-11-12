package tech.jhipster.forge.generator.project.infrastructure.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.forge.generator.project.domain.service.init.InitDomainService;
import tech.jhipster.forge.generator.project.domain.service.init.InitRepository;
import tech.jhipster.forge.generator.project.domain.service.init.InitService;

@Configuration
public class InitServiceBeanConfiguration {

  private final InitRepository initRepository;

  public InitServiceBeanConfiguration(InitRepository initRepository) {
    this.initRepository = initRepository;
  }

  @Bean
  public InitService initService() {
    return new InitDomainService(initRepository);
  }
}
