package tech.jhipster.forge.generator.project.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.forge.TestUtils.assertFileContent;
import static tech.jhipster.forge.TestUtils.assertFileExist;
import static tech.jhipster.forge.common.domain.FileUtils.tmpDirForTest;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.TestUtils;
import tech.jhipster.forge.generator.project.application.InitApplicationService;
import tech.jhipster.forge.generator.project.application.MavenApplicationService;
import tech.jhipster.forge.generator.project.application.SpringBootApplicationService;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class SpringBootResourceIT {

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldAddSpringBoot() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    initApplicationService.init(project);
    mavenApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, "pom.xml");
    assertFileContent(projectPath, "pom.xml", List.of("<groupId>tech.jhipster.chips</groupId>", "<artifactId>chips</artifactId>"));
    assertFileContent(
      projectPath,
      "pom.xml",
      List.of("<groupId>org.springframework.boot</groupId>", "<artifactId>spring-boot-starter</artifactId>")
    );

    assertFileExist(project, "src/main/java/tech/jhipster/chips/ChipsApp.java");
    assertFileExist(project, "src/test/java/tech/jhipster/chips/ChipsAppIT.java");

    assertFileExist(project, "src/main/resources/config/application.properties");
  }
}
