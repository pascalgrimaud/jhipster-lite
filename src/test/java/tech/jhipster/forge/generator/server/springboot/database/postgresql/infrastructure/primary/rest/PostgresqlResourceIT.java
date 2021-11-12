package tech.jhipster.forge.generator.server.springboot.database.postgresql.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.forge.TestUtils.assertFileExist;
import static tech.jhipster.forge.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.generator.project.domain.model.Constants.MAIN_JAVA;
import static tech.jhipster.forge.generator.project.domain.model.Constants.TEST_JAVA;

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
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.forge.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
@AutoConfigureMockMvc
class PostgresqlResourceIT {

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

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

    mockMvc
      .perform(
        post("/api/servers/spring-boot/databases/postgresql")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, getPath(MAIN_JAVA, "tech/jhipster/chips/technical/secondary/postgresql/FixedPostgreSQL10Dialect.java"));
    assertFileExist(
      projectPath,
      getPath(TEST_JAVA, "tech/jhipster/chips/technical/secondary/postgresql/FixedPostgreSQL10DialectTest.java")
    );

    assertFileExist(projectPath, "src/main/docker/postgresql.yml");
  }
}
