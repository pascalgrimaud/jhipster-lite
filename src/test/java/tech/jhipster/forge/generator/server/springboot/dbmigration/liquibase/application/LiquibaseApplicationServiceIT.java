package tech.jhipster.forge.generator.server.springboot.dbmigration.liquibase.application;

import static tech.jhipster.forge.TestUtils.*;
import static tech.jhipster.forge.generator.project.infrastructure.secondary.buildtool.maven.MavenLocalRepository.POM_XML;
import static tech.jhipster.forge.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.assertFilesLiquibaseChangelogMasterXml;
import static tech.jhipster.forge.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.assertFilesLiquibaseJava;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.generator.buildtool.generic.domain.BuildToolRepository;
import tech.jhipster.forge.generator.project.application.LiquibaseApplicationService;
import tech.jhipster.forge.generator.project.domain.model.BuildToolType;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.core.SpringBootRepository;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.database.postgresql.PostgresqlService;

@IntegrationTest
class LiquibaseApplicationServiceIT {

  @Autowired
  BuildToolRepository buildToolRepository;

  @Autowired
  SpringBootRepository springBootRepository;

  @Autowired
  PostgresqlService postgresqlService;

  @Autowired
  LiquibaseApplicationService liquibaseApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProjectBuilder().build();
    buildToolRepository.init(project, BuildToolType.MAVEN);
    springBootRepository.init(project);
    postgresqlService.init(project);

    liquibaseApplicationService.init(project);

    assertFileContent(
      project,
      POM_XML,
      List.of("<dependency>", "<groupId>org.liquibase</groupId>", "<artifactId>liquibase-core</artifactId>", "</dependency>")
    );
    assertFileContent(
      project,
      POM_XML,
      List.of("<dependency>", "<groupId>com.h2database</groupId>", "<artifactId>h2</artifactId>", "<scope>test</scope>", "</dependency>")
    );
    assertFilesLiquibaseChangelogMasterXml(project);
    assertFilesLiquibaseJava(project);
  }

  @Test
  void shouldAddLiquibase() {
    Project project = tmpProjectBuilder().build();
    buildToolRepository.init(project, BuildToolType.MAVEN);

    liquibaseApplicationService.addLiquibase(project);

    assertFileContent(
      project,
      POM_XML,
      List.of("<dependency>", "<groupId>org.liquibase</groupId>", "<artifactId>liquibase-core</artifactId>", "</dependency>")
    );
  }

  @Test
  void shouldAddChangelogMasterXml() {
    Project project = tmpProject();

    liquibaseApplicationService.addChangelogMasterXml(project);

    assertFilesLiquibaseChangelogMasterXml(project);
  }

  @Test
  void shouldAddConfigurationJava() {
    Project project = tmpProject();

    liquibaseApplicationService.addConfigurationJava(project);

    assertFilesLiquibaseJava(project);
  }
}
