package tech.jhipster.forge.generator.server.springboot.database.postgresql.infrastructure.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.forge.generator.buildtool.generic.domain.BuildToolRepository;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.database.postgresql.PostgresqlService;
import tech.jhipster.forge.generator.project.infrastructure.secondary.server.springboot.database.PostgresqlDomainService;
import tech.jhipster.forge.generator.server.springboot.properties.domain.SpringBootPropertiesService;

@Configuration
public class PostgresqlServiceBeanConfiguration {

  public final ProjectRepository projectRepository;
  public final BuildToolRepository buildToolRepository;
  public final SpringBootPropertiesService springBootPropertiesService;

  public PostgresqlServiceBeanConfiguration(
    ProjectRepository projectRepository,
    BuildToolRepository buildToolRepository,
    SpringBootPropertiesService springBootPropertiesService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolRepository = buildToolRepository;
    this.springBootPropertiesService = springBootPropertiesService;
  }

  @Bean
  public PostgresqlService postgresqlService() {
    return new PostgresqlDomainService(projectRepository, buildToolRepository, springBootPropertiesService);
  }
}
