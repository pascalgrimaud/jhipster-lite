package tech.jhipster.forge.generator.server.springboot.web.application;

import static tech.jhipster.forge.TestUtils.assertFileContent;
import static tech.jhipster.forge.TestUtils.tmpProject;
import static tech.jhipster.forge.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.generator.project.domain.model.Constants.MAIN_RESOURCES;
import static tech.jhipster.forge.generator.project.domain.model.Constants.TEST_RESOURCES;
import static tech.jhipster.forge.generator.server.springboot.core.domain.SpringBoot.APPLICATION_PROPERTIES;
import static tech.jhipster.forge.generator.server.springboot.web.application.SpringBootWebAssertFiles.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.forge.generator.project.application.InitApplicationService;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
class SpringBootWebApplicationServiceIT {

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  SpringBootWebApplicationService springBootWebApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootWebApplicationService.init(project);

    assertFileContent(project, "pom.xml", springBootStarterWebDependency());
    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=8080");
  }

  @Test
  void shouldAddSpringBootWeb() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootWebApplicationService.addSpringBootWeb(project);

    assertFileContent(project, "pom.xml", springBootStarterWebDependency());
    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=8080");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
  }

  @Test
  void shouldAddSpringBootWebWithServerPort() {
    Project project = tmpProject();
    project.addConfig("serverPort", 7419);
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootWebApplicationService.addSpringBootWeb(project);

    assertFileContent(project, "pom.xml", springBootStarterWebDependency());
    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=7419");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
  }

  @Test
  void shouldAddSpringBootWebWithInvalidServerPort() {
    Project project = tmpProject();
    project.addConfig("serverPort", "chips");
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootWebApplicationService.addSpringBootWeb(project);

    assertFileContent(project, "pom.xml", springBootStarterWebDependency());
    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=8080");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
  }

  @Test
  void shouldAddSpringBootUndertow() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootWebApplicationService.addSpringBootUndertow(project);

    assertFileContent(project, "pom.xml", springBootStarterWebWithoutTomcat());
    assertFileContent(project, "pom.xml", springBootStarterUndertowDependency());
    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=8080");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
  }

  @Test
  void shouldAddSpringBootUndertowWithServerPort() {
    Project project = tmpProject();
    project.addConfig("serverPort", 1664);
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootWebApplicationService.addSpringBootUndertow(project);

    assertFileContent(project, "pom.xml", springBootStarterWebWithoutTomcat());
    assertFileContent(project, "pom.xml", springBootStarterUndertowDependency());
    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=1664");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
  }
}
