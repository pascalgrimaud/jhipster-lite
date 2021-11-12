package tech.jhipster.forge.generator.server.springboot.core.infrastructure.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.forge.generator.buildtool.generic.domain.BuildToolRepository;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;
import tech.jhipster.forge.generator.server.springboot.core.domain.SpringBootDomainService;
import tech.jhipster.forge.generator.server.springboot.core.domain.SpringBootService;

@Configuration
public class SpringBootServiceBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final BuildToolRepository buildToolRepository;

  public SpringBootServiceBeanConfiguration(ProjectRepository projectRepository, BuildToolRepository buildToolRepository) {
    this.projectRepository = projectRepository;
    this.buildToolRepository = buildToolRepository;
  }

  @Bean
  public SpringBootService springBootService() {
    return new SpringBootDomainService(projectRepository, buildToolRepository);
  }
}
