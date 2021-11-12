package tech.jhipster.forge.generator.project.infrastructure.secondary.server.springboot.core;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.forge.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.error.domain.GeneratorException;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.infrastructure.secondary.buildtool.maven.MavenLocalRepository;

@IntegrationTest
class SpringBootLocalRepositoryIT {

  @Autowired
  MavenLocalRepository mavenLocalRepository;

  @Autowired
  SpringBootLocalRepository springBootLocalRepository;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    project.addConfig("springBootVersion", "2.5.3");
    mavenLocalRepository.addPomXml(project);

    springBootLocalRepository.init(project);

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
    mavenLocalRepository.addPomXml(project);

    springBootLocalRepository.addSpringBootParent(project);
    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-starter-parent</artifactId>");
    assertFileContent(project, "pom.xml", "<version>2.5.3</version>");

    // add again the parent, with wrong version
    project.addConfig("springBootVersion", "X.X.X");
    springBootLocalRepository.addSpringBootParent(project);
    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-starter-parent</artifactId>");
    assertFileNoContent(project, "pom.xml", "<version>X.X.X</version>");
  }

  @Test
  void shouldNotAddSpringBootParentWhenNoPomXml() {
    Project project = tmpProject();

    assertThatThrownBy(() -> springBootLocalRepository.addSpringBootParent(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddSpringBootDependencies() {
    Project project = tmpProject();
    mavenLocalRepository.addPomXml(project);

    springBootLocalRepository.addSpringBootDependencies(project);

    assertFileContent(project, "pom.xml", "<groupId>org.springframework.boot</groupId>");
    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-starter</artifactId>");

    assertFileContent(project, "pom.xml", "<groupId>org.apache.commons</groupId>");
    assertFileContent(project, "pom.xml", "<artifactId>commons-lang3</artifactId>");

    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-starter-test</artifactId>");
  }

  @Test
  void shouldNotAddSpringBootDependenciesWhenNoPomXml() {
    Project project = tmpProject();

    assertThatThrownBy(() -> springBootLocalRepository.addSpringBootDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddSpringBootPlugin() {
    Project project = tmpProject();
    mavenLocalRepository.addPomXml(project);

    springBootLocalRepository.addSpringBootMavenPlugin(project);

    assertFileContent(project, "pom.xml", "<groupId>org.springframework.boot</groupId>");
    assertFileContent(project, "pom.xml", "<artifactId>spring-boot-maven-plugin</artifactId>");
  }

  @Test
  void shouldNotAddSpringBootPluginWhenNoPomXml() {
    Project project = tmpProject();

    assertThatThrownBy(() -> springBootLocalRepository.addSpringBootMavenPlugin(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddMainApp() {
    Project project = tmpProject();
    mavenLocalRepository.addPomXml(project);

    springBootLocalRepository.addMainApp(project);

    assertFileExist(project, "src/main/java/com/mycompany/myapp/JhipsterApp.java");
    assertFileExist(project, "src/test/java/com/mycompany/myapp/JhipsterAppIT.java");
  }

  @Test
  void shouldAddApplicationProperties() {
    Project project = tmpProject();

    springBootLocalRepository.addApplicationProperties(project);

    assertFileExist(project, "src/main/resources/config/application.properties");
  }

  @Test
  void shouldAddApplicationTestProperties() {
    Project project = tmpProject();

    springBootLocalRepository.addApplicationTestProperties(project);

    assertFileExist(project, "src/test/resources/config/application.properties");
  }
}
