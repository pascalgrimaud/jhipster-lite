package tech.jhipster.forge.generator.project.infrastructure.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.forge.generator.project.domain.service.buildtool.maven.MavenDomainService;
import tech.jhipster.forge.generator.project.domain.service.buildtool.maven.MavenRepository;
import tech.jhipster.forge.generator.project.domain.service.buildtool.maven.MavenService;

@Configuration
public class MavenServiceBeanConfiguration {

  private final MavenRepository mavenRepository;

  public MavenServiceBeanConfiguration(MavenRepository mavenRepository) {
    this.mavenRepository = mavenRepository;
  }

  @Bean
  public MavenService mavenService() {
    return new MavenDomainService(mavenRepository);
  }
}
