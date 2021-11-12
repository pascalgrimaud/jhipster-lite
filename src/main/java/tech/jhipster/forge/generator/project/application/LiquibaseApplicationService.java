package tech.jhipster.forge.generator.project.application;

import org.springframework.stereotype.Service;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.server.springboot.dbmigration.liquibase.domain.LiquibaseService;

@Service
public class LiquibaseApplicationService {

  private final LiquibaseService liquibaseService;

  public LiquibaseApplicationService(LiquibaseService liquibaseService) {
    this.liquibaseService = liquibaseService;
  }

  public void init(Project project) {
    liquibaseService.init(project);
  }

  public void addLiquibase(Project project) {
    liquibaseService.addLiquibase(project);
  }

  public void addChangelogMasterXml(Project project) {
    liquibaseService.addChangelogMasterXml(project);
  }

  public void addConfigurationJava(Project project) {
    liquibaseService.addConfigurationJava(project);
  }
}
