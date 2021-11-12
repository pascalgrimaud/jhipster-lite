package tech.jhipster.forge.generator.server.springboot.dbmigration.liquibase.domain;

import tech.jhipster.forge.generator.project.domain.model.Project;

public interface LiquibaseService {
  void init(Project project);

  void addLiquibase(Project project);
  void addChangelogMasterXml(Project project);
  void addConfigurationJava(Project project);
}
