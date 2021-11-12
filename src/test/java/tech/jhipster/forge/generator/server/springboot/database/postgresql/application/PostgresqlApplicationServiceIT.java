package tech.jhipster.forge.generator.server.springboot.database.postgresql.application;

import static tech.jhipster.forge.TestUtils.*;
import static tech.jhipster.forge.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.generator.project.domain.model.Constants.*;
import static tech.jhipster.forge.generator.project.domain.model.DefaultConfig.BASE_NAME;
import static tech.jhipster.forge.generator.project.domain.model.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.forge.generator.project.domain.service.server.springboot.core.SpringBoot.APPLICATION_PROPERTIES;
import static tech.jhipster.forge.generator.project.infrastructure.secondary.buildtool.maven.MavenLocalRepository.POM_XML;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.generator.project.application.InitApplicationService;
import tech.jhipster.forge.generator.project.application.MavenApplicationService;
import tech.jhipster.forge.generator.project.application.PostgresqlApplicationService;
import tech.jhipster.forge.generator.project.application.SpringBootApplicationService;
import tech.jhipster.forge.generator.project.domain.model.Project;

@IntegrationTest
class PostgresqlApplicationServiceIT {

  @Autowired
  PostgresqlApplicationService postgresqlApplicationService;

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
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    postgresqlApplicationService.init(project);

    assertFileContent(project, "pom.xml", springBootStarterDataJpa());
    assertFileContent(project, "pom.xml", postgreSQLDriver());
    assertFileContent(project, "pom.xml", hikari());
    assertFileContent(project, "pom.xml", hibernateCore());

    assertFileExist(project, "src/main/docker/postgresql.yml");
    assertFileContent(project, "src/main/docker/postgresql.yml", "POSTGRES_USER=jhipster");

    assertFileExist(project, getPath(MAIN_JAVA, "com/mycompany/myapp", "/technical/secondary/postgresql/FixedPostgreSQL10Dialect.java"));
    assertFileExist(
      project,
      getPath(TEST_JAVA, "com/mycompany/myapp", "/technical/secondary/postgresql/FixedPostgreSQL10DialectTest.java")
    );
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      "spring.datasource.url=jdbc:postgresql://localhost:5432/jhipster"
    );
    assertFileContent(project, POM_XML, "<testcontainers.version>");
    assertFileContent(project, POM_XML, "</testcontainers.version>");
    assertFileContent(project, POM_XML, testcontainers());
    assertFileContent(
      project,
      getPath(TEST_RESOURCES, "config/application.properties"),
      List.of("spring.datasource.url=jdbc:tc:postgresql:13.4:///jhipster?TC_TMPFS=/testtmpfs:rw", "spring.datasource.username=jhipster")
    );
  }

  @Test
  void shouldAddSpringDataJpa() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    postgresqlApplicationService.addSpringDataJpa(project);

    assertFileContent(project, "pom.xml", springBootStarterDataJpa());
  }

  @Test
  @DisplayName("should add postgresql driver")
  void shouldAddPostgreSQLDriver() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    postgresqlApplicationService.addPostgreSQLDriver(project);

    assertFileContent(project, "pom.xml", postgreSQLDriver());
  }

  @Test
  void shouldAddHikari() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    postgresqlApplicationService.addHikari(project);

    assertFileContent(project, "pom.xml", hikari());
  }

  @Test
  void shouldAddHibernateCore() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    postgresqlApplicationService.addHibernateCore(project);

    assertFileContent(project, "pom.xml", hibernateCore());
  }

  @Test
  void shouldAddDockerCompose() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "chips");

    postgresqlApplicationService.addDockerCompose(project);

    assertFileExist(project, "src/main/docker/postgresql.yml");
    assertFileContent(project, "src/main/docker/postgresql.yml", "POSTGRES_USER=chips");
  }

  @Test
  void shouldAddDialectJava() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "chips");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.chips");

    postgresqlApplicationService.addDialectJava(project);

    assertFileExist(project, getPath(MAIN_JAVA, "tech/jhipster/chips", "/technical/secondary/postgresql/FixedPostgreSQL10Dialect.java"));
    assertFileExist(
      project,
      getPath(TEST_JAVA, "tech/jhipster/chips", "/technical/secondary/postgresql/FixedPostgreSQL10DialectTest.java")
    );
  }

  @Test
  void shouldAddProperties() {
    Project project = tmpProject();
    project.addConfig(PACKAGE_NAME, "tech.jhipster.chips");
    project.addConfig(BASE_NAME, "chips");
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    postgresqlApplicationService.addProperties(project);

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      List.of(
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "spring.datasource.hikari.auto-commit=false",
        "spring.datasource.hikari.poolName=Hikari",
        "spring.datasource.password=",
        "spring.datasource.type=com.zaxxer.hikari.HikariDataSource",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/chips",
        "spring.datasource.username=chips",
        "spring.jpa.database-platform=tech.jhipster.chips.technical.secondary.postgresql.FixedPostgreSQL10Dialect"
      )
    );

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      List.of("spring.jpa.database-platform=tech.jhipster.chips.technical.secondary.postgresql.FixedPostgreSQL10Dialect")
    );
  }

  @Test
  void shouldAddTestcontainers() {
    Project project = tmpProject();
    project.addConfig(PACKAGE_NAME, "tech.jhipster.chips");
    project.addConfig(BASE_NAME, "chips");
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.addApplicationTestProperties(project);

    postgresqlApplicationService.addTestContainers(project);

    assertFileContent(project, POM_XML, "<testcontainers.version>");
    assertFileContent(project, POM_XML, "</testcontainers.version>");
    assertFileContent(project, POM_XML, testcontainers());
    assertFileContent(
      project,
      getPath(TEST_RESOURCES, "config/application.properties"),
      List.of("spring.datasource.url=jdbc:tc:postgresql:13.4:///chips?TC_TMPFS=/testtmpfs:rw", "spring.datasource.username=chips")
    );
  }

  private List<String> springBootStarterDataJpa() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-data-jpa</artifactId>",
      "</dependency>"
    );
  }

  private List<String> postgreSQLDriver() {
    return List.of("<dependency>", "<groupId>org.postgresql</groupId>", "<artifactId>postgresql</artifactId>", "</dependency>");
  }

  private List<String> hikari() {
    return List.of("<dependency>", "<groupId>com.zaxxer</groupId>", "<artifactId>HikariCP</artifactId>", "</dependency>");
  }

  private List<String> hibernateCore() {
    return List.of("<dependency>", "<groupId>org.hibernate</groupId>", "<artifactId>hibernate-core</artifactId>", "</dependency>");
  }

  private List<String> testcontainers() {
    return List.of(
      "<dependency>",
      "<groupId>org.testcontainers</groupId>",
      "<artifactId>postgresql</artifactId>",
      "<version>${testcontainers.version}</version>",
      "<scope>test</scope>",
      "</dependency>"
    );
  }
}
