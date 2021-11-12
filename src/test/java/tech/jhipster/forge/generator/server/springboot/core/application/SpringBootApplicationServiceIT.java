package tech.jhipster.forge.generator.server.springboot.core.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.forge.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.error.domain.GeneratorException;
import tech.jhipster.forge.generator.project.application.InitApplicationService;
import tech.jhipster.forge.generator.project.application.MavenApplicationService;
import tech.jhipster.forge.generator.project.domain.model.Project;

@IntegrationTest
class SpringBootApplicationServiceIT {

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    project.addConfig("springBootVersion", "2.5.3");
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    springBootApplicationService.init(project);

    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-starter-parent</artifactId>");
    assertFileContent(project, "pom.xml", "<version>2.5.3</version>");

    assertFileContent(project, "pom.xml", "<groupId>org.springframework.boot</groupId>");
    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-starter</artifactId>");
    assertFileContent(project, "pom.xml", "<groupId>org.apache.commons</groupId>");
    assertFileContent(project, "pom.xml", "<artifactId>commons-lang3</artifactId>");
    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-starter-test</artifactId>");

    assertFileContent(project, "pom.xml", "<groupId>org.springframework.boot</groupId>");
    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-maven-plugin</artifactId>");

    assertFileExist(project, "src/main/java/com/mycompany/myapp/JhipsterApp.java");
    assertFileExist(project, "src/test/java/com/mycompany/myapp/JhipsterAppIT.java");

    assertFileExist(project, "src/main/resources/config/application.properties");
  }

  @Test
  void shouldAddSpringBootParent() {
    Project project = tmpProject();
    project.addConfig("springBootVersion", "2.5.3");
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    springBootApplicationService.addSpringBootParent(project);
    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-starter-parent</artifactId>");
    assertFileContent(project, "pom.xml", "<version>2.5.3</version>");

    // add again the parent, with wrong version
    project.addConfig("springBootVersion", "X.X.X");
    springBootApplicationService.addSpringBootParent(project);
    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-starter-parent</artifactId>");
    assertFileNoContent(project, "pom.xml", "<version>X.X.X</version>");
  }

  @Test
  void shouldNotAddSpringBootParentWhenNoPomXml() {
    Project project = tmpProject();

    assertThatThrownBy(() -> springBootApplicationService.addSpringBootParent(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddSpringBootDependencies() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    springBootApplicationService.addSpringBootDependencies(project);

    assertFileContent(project, "pom.xml", "<groupId>org.springframework.boot</groupId>");
    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-starter</artifactId>");

    assertFileContent(project, "pom.xml", "<groupId>org.apache.commons</groupId>");
    assertFileContent(project, "pom.xml", "<artifactId>commons-lang3</artifactId>");

    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-starter-test</artifactId>");
  }

  @Test
  void shouldNotAddSpringBootDependenciesWhenNoPomXml() {
    Project project = tmpProject();

    assertThatThrownBy(() -> springBootApplicationService.addSpringBootDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddSpringBootPlugin() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    springBootApplicationService.addSpringBootMavenPlugin(project);

    assertFileContent(project, "pom.xml", "<groupId>org.springframework.boot</groupId>");
    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-maven-plugin</artifactId>");
  }

  @Test
  void shouldNotAddSpringBootPluginWhenNoPomXml() {
    Project project = tmpProject();

    assertThatThrownBy(() -> springBootApplicationService.addSpringBootMavenPlugin(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddMainApp() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    springBootApplicationService.addMainApp(project);

    assertFileExist(project, "src/main/java/com/mycompany/myapp/JhipsterApp.java");
    assertFileExist(project, "src/test/java/com/mycompany/myapp/JhipsterAppIT.java");
  }

  @Test
  void shouldAddApplicationProperties() {
    Project project = tmpProject();
    initApplicationService.init(project);

    springBootApplicationService.addApplicationProperties(project);

    assertFileExist(project, "src/main/resources/config/application.properties");
  }

  @Test
  void shouldAddApplicationTestProperties() {
    Project project = tmpProject();
    initApplicationService.init(project);

    springBootApplicationService.addApplicationTestProperties(project);

    assertFileExist(project, "src/test/resources/config/application.properties");
  }
  //  @Nested
  //  class Properties {
  //
  //    @Test
  //    void shouldAddPropertiesWithInteger() {
  //      Project project = tmpProject();
  //      springBootApplicationService.addApplicationProperties(project);
  //
  //      springBootApplicationService.addProperties(project, "server.port", 8080);
  //
  //      String applicationProperties = getPath(MAIN_RESOURCES, "config/application.properties");
  //      assertFileContent(project, applicationProperties, "server.port=8080");
  //      assertFileContent(project, applicationProperties, "# jhipster-needle-application-properties");
  //    }
  //
  //    @Test
  //    void shouldAddPropertiesWithBoolean() {
  //      Project project = tmpProject();
  //      springBootApplicationService.addApplicationProperties(project);
  //
  //      springBootApplicationService.addProperties(project, "spring.jmx.enabled", false);
  //
  //      String applicationProperties = getPath(MAIN_RESOURCES, "config/application.properties");
  //      assertFileContent(project, applicationProperties, "spring.jmx.enabled=false");
  //      assertFileContent(project, applicationProperties, "# jhipster-needle-application-properties");
  //    }
  //
  //    @Test
  //    void shouldAddPropertiesWithString() {
  //      Project project = tmpProject();
  //      springBootApplicationService.addApplicationProperties(project);
  //
  //      springBootApplicationService.addProperties(project, "jhipster.application", "jhforge");
  //
  //      String applicationProperties = getPath(MAIN_RESOURCES, "config/application.properties");
  //      assertFileContent(project, applicationProperties, "jhipster.application=jhforge");
  //      assertFileContent(project, applicationProperties, "# jhipster-needle-application-properties");
  //    }
  //
  //    @Test
  //    void shouldNotAddPropertiesWhenNoApplicationProperties() {
  //      Project project = tmpProject();
  //
  //      assertThatThrownBy(() -> springBootApplicationService.addProperties(project, "jhipster.application", "jhforge"))
  //        .isExactlyInstanceOf(GeneratorException.class);
  //    }
  //  }

  //  @Nested
  //  class TestProperties {
  //
  //    @Test
  //    void shouldAddPropertiesTestWithInteger() {
  //      Project project = tmpProject();
  //      springBootApplicationService.addApplicationTestProperties(project);
  //
  //      springBootApplicationService.addPropertiesTest(project, "server.port", 8080);
  //
  //      String applicationProperties = getPath(TEST_RESOURCES, "config/application.properties");
  //      assertFileContent(project, applicationProperties, "server.port=8080");
  //      assertFileContent(project, applicationProperties, "# jhipster-needle-application-test-properties");
  //    }
  //
  //    @Test
  //    void shouldAddPropertiesTestWithBoolean() {
  //      Project project = tmpProject();
  //      springBootApplicationService.addApplicationTestProperties(project);
  //
  //      springBootApplicationService.addPropertiesTest(project, "spring.jmx.enabled", false);
  //
  //      String applicationProperties = getPath(TEST_RESOURCES, "config/application.properties");
  //      assertFileContent(project, applicationProperties, "spring.jmx.enabled=false");
  //      assertFileContent(project, applicationProperties, "# jhipster-needle-application-test-properties");
  //    }
  //
  //    @Test
  //    void shouldAddPropertiesTestWithString() {
  //      Project project = tmpProject();
  //      springBootApplicationService.addApplicationTestProperties(project);
  //
  //      springBootApplicationService.addPropertiesTest(project, "jhipster.application", "jhforge");
  //
  //      String applicationProperties = getPath(TEST_RESOURCES, "config/application.properties");
  //      assertFileContent(project, applicationProperties, "jhipster.application=jhforge");
  //      assertFileContent(project, applicationProperties, "# jhipster-needle-application-test-properties");
  //    }
  //
  //    @Test
  //    void shouldNotAddPropertiesTestWhenNoApplicationProperties() {
  //      Project project = tmpProject();
  //
  //      assertThatThrownBy(() -> springBootApplicationService.addPropertiesTest(project, "jhipster.application", "jhforge"))
  //        .isExactlyInstanceOf(GeneratorException.class);
  //    }
  //  }
}
