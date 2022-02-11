package tech.jhipster.lite.generator.server.springboot.broker.kafka.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.APPLICATION_PROPERTIES;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
public class KafkaApplicationServiceIT {

  @Autowired
  KafkaApplicationService kafkaApplicationService;

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    kafkaApplicationService.init(project);
    assertFileContent(project, POM_XML, kafkaClients());

    assertFileExist(project, "src/main/docker/kafka.yml");
    assertFileContent(project, "src/main/docker/kafka.yml", "KAFKA_BROKER_ID: 1");

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "kafka.bootstrap-servers=localhost:9092");
    assertFileContent(project, POM_XML, "<testcontainers.version>");
    assertFileContent(project, POM_XML, "</testcontainers.version>");
    assertFileContent(project, POM_XML, testcontainers());
  }

  @Test
  void shouldAddProducer() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);
    kafkaApplicationService.init(project);

    kafkaApplicationService.addProducer(project);

    assertFileExist(project, "src/main/" + project.getPackageNamePath() + "/config/KafkaProperties.java");
    assertFileContent(project, "src/main/" + project.getPackageNamePath() + "/config/KafkaProperties.java", "public class KafkaProperties");

    assertFileExist(project, "src/main/" + project.getPackageNamePath() + "/service/kafka/producer/DummyProducer.java");
    assertFileContent(
      project,
      "src/main/" + project.getPackageNamePath() + "/service/kafka/producer/DummyProducer.java",
      "public class DummyProducer"
    );
  }

  private List<String> kafkaClients() {
    return List.of("<dependency>", "<groupId>org.apache.kafka</groupId>", "<artifactId>kafka-clients</artifactId>", "</dependency>");
  }

  private List<String> testcontainers() {
    return List.of(
      "<dependency>",
      "<groupId>org.testcontainers</groupId>",
      "<artifactId>kafka</artifactId>",
      "<version>${testcontainers.version}</version>",
      "<scope>test</scope>",
      "</dependency>"
    );
  }
}
