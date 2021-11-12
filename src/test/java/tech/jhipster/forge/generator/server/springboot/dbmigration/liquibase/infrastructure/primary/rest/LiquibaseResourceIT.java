package tech.jhipster.forge.generator.server.springboot.dbmigration.liquibase.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.forge.TestUtils.assertFileContent;
import static tech.jhipster.forge.generator.project.infrastructure.secondary.buildtool.maven.MavenLocalRepository.POM_XML;
import static tech.jhipster.forge.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.assertFilesLiquibaseChangelogMasterXml;
import static tech.jhipster.forge.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.assertFilesLiquibaseJava;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.TestUtils;
import tech.jhipster.forge.common.domain.FileUtils;
import tech.jhipster.forge.error.domain.GeneratorException;
import tech.jhipster.forge.generator.project.application.InitApplicationService;
import tech.jhipster.forge.generator.project.application.MavenApplicationService;
import tech.jhipster.forge.generator.project.application.PostgresqlApplicationService;
import tech.jhipster.forge.generator.project.application.SpringBootApplicationService;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class LiquibaseResourceIT {

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  PostgresqlApplicationService postgresqlApplicationService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    projectDTO.folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    initApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);
    postgresqlApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/dbmigration/liquibase")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

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
}
