package tech.jhipster.lite.generator.server.springboot.broker.kafka.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.APPLICATION_PROPERTIES;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.postgresql.domain.Postgresql;

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
    //    assertFileContent(project, POM_XML, springBootStarterDataJpa());
    //    assertFileContent(project, POM_XML, postgreSQLDriver());
    //    assertFileContent(project, POM_XML, hikari());
    //    assertFileContent(project, POM_XML, hibernateCore());
    //
    //    assertFileExist(project, "src/main/docker/postgresql.yml");
    //    assertFileContent(project, "src/main/docker/postgresql.yml", "POSTGRES_USER=jhipster");
    //
    //    String postgresqlPath = getPath("com/mycompany/myapp/technical/infrastructure/secondary/postgresql");
    //    assertFileExist(project, getPath(MAIN_JAVA, postgresqlPath, "DatabaseConfiguration.java"));
    //    assertFileExist(project, getPath(MAIN_JAVA, postgresqlPath, "FixedPostgreSQL10Dialect.java"));
    //    assertFileExist(project, getPath(TEST_JAVA, postgresqlPath, "FixedPostgreSQL10DialectTest.java"));
    //
    //    assertFileContent(
    //      project,
    //      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
    //      "spring.datasource.url=jdbc:postgresql://localhost:5432/jhipster"
    //    );
    //    assertFileContent(project, POM_XML, "<testcontainers.version>");
    //    assertFileContent(project, POM_XML, "</testcontainers.version>");
    //    assertFileContent(project, POM_XML, testcontainers());
    //    assertFileContent(
    //      project,
    //      getPath(TEST_RESOURCES, "config/application.properties"),
    //      List.of(
    //        "spring.datasource.url=jdbc:tc:postgresql:" + Postgresql.getPostgresqlDockerVersion() + ":///jhipster?TC_TMPFS=/testtmpfs:rw",
    //        "spring.datasource.username=jhipster"
    //      )
    //    );
    //    assertLoggerInConfig(project);
  }
}
