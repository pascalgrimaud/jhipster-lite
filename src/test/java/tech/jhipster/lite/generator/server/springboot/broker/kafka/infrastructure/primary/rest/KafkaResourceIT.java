package tech.jhipster.lite.generator.server.springboot.broker.kafka.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileExist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.application.KafkaApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
@AutoConfigureMockMvc
public class KafkaResourceIT {

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  KafkaApplicationService kafkaApplicationService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldInitKafka() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    Project project = ProjectDTO.toProject(projectDTO);
    initApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/brokers/kafka")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, "src/main/docker/kafka.yml");
  }

  @Test
  void shouldAddProducer() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    Project project = ProjectDTO.toProject(projectDTO);
    initApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);
    kafkaApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/brokers/kafka/producers")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, "src/main/" + project.getPackageNamePath() + "/config/KafkaProperties.java");
    assertFileExist(projectPath, "src/main/" + project.getPackageNamePath() + "/service/kafka/producer/DummyProducer.java");
  }
}
