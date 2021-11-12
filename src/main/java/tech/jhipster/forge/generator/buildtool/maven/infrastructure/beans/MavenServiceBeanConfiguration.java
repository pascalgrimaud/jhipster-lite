package tech.jhipster.forge.generator.buildtool.maven.infrastructure.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.forge.generator.buildtool.maven.domain.MavenDomainService;
import tech.jhipster.forge.generator.buildtool.maven.domain.MavenService;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;

@Configuration
public class MavenServiceBeanConfiguration {

  private final ProjectRepository projectRepository;

  public MavenServiceBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public MavenService mavenService() {
    return new MavenDomainService(projectRepository);
  }
}
