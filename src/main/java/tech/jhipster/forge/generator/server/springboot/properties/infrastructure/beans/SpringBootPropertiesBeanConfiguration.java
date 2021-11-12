package tech.jhipster.forge.generator.server.springboot.properties.infrastructure.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;
import tech.jhipster.forge.generator.server.springboot.properties.domain.SpringBootPropertiesDomainService;
import tech.jhipster.forge.generator.server.springboot.properties.domain.SpringBootPropertiesService;

@Configuration
public class SpringBootPropertiesBeanConfiguration {

  private final ProjectRepository projectRepository;

  public SpringBootPropertiesBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public SpringBootPropertiesService springBootPropertiesService() {
    return new SpringBootPropertiesDomainService(projectRepository);
  }
}
