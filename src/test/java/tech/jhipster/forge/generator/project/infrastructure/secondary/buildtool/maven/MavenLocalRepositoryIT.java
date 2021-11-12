package tech.jhipster.forge.generator.project.infrastructure.secondary.buildtool.maven;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.forge.TestUtils.*;
import static tech.jhipster.forge.generator.buildtool.maven.application.MavenAssertFiles.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.error.domain.GeneratorException;
import tech.jhipster.forge.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.forge.generator.buildtool.generic.domain.Parent;
import tech.jhipster.forge.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.forge.generator.project.domain.model.Project;

@IntegrationTest
class MavenLocalRepositoryIT {

  @Autowired
  MavenLocalRepository mavenLocalRepository;

  @Test
  void shouldAddParent() throws Exception {
    Project project = tmpProjectWithPomXml();

    Parent parent = Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("2.5.3").build();
    mavenLocalRepository.addParent(project, parent);

    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<parent>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-parent</artifactId>",
        "<version>2.5.3</version>",
        "<relativePath/>",
        "</parent>"
      )
    );
  }

  @Test
  void shouldNotAddParent() throws Exception {
    Project project = tmpProject();

    Parent parent = Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("2.5.3").build();
    assertThatThrownBy(() -> mavenLocalRepository.addParent(project, parent)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddDependency() throws Exception {
    Project project = tmpProjectWithPomXml();

    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();
    mavenLocalRepository.addDependency(project, dependency);

    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter</artifactId>",
        "</dependency>"
      )
    );
  }

  @Test
  void shouldNotAddDependency() throws Exception {
    Project project = tmpProject();

    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();
    assertThatThrownBy(() -> mavenLocalRepository.addDependency(project, dependency)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddDependencyWithScopeTest() throws Exception {
    Project project = tmpProjectWithPomXml();

    Dependency dependency = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-test")
      .scope("test")
      .build();
    mavenLocalRepository.addDependency(project, dependency);

    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-test</artifactId>",
        "<scope>test</scope>",
        "</dependency>"
      )
    );
  }

  @Test
  void shouldAddDependencyWithExclusions() throws Exception {
    Project project = tmpProjectWithPomXml();

    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-web").build();
    Dependency dependencyToExclude = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-tomcat")
      .build();
    mavenLocalRepository.addDependency(project, dependency, List.of(dependencyToExclude));

    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-web</artifactId>",
        "<exclusions>",
        "<exclusion>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-tomcat</artifactId>",
        "</exclusion>",
        "</exclusions>",
        "</dependency>"
      )
    );
  }

  @Test
  void shouldNotAddDependencyWithExclusions() throws Exception {
    Project project = tmpProject();

    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-web").build();
    Dependency dependencyToExclude = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-tomcat")
      .build();
    assertThatThrownBy(() -> mavenLocalRepository.addDependency(project, dependency, List.of(dependencyToExclude)))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddPlugin() throws Exception {
    Project project = tmpProjectWithPomXml();

    Plugin plugin = Plugin.builder().groupId("org.springframework.boot").artifactId("spring-boot-maven-plugin").build();
    mavenLocalRepository.addPlugin(project, plugin);

    assertFileContent(
      project,
      "pom.xml",
      List.of("<plugin>", "<groupId>org.springframework.boot</groupId>", "<artifactId>spring-boot-maven-plugin</artifactId>", "</plugin>")
    );
  }

  @Test
  void shouldNotAddPlugin() {
    Project project = tmpProject();

    Plugin plugin = Plugin.builder().groupId("org.springframework.boot").artifactId("spring-boot-maven-plugin").build();

    assertThatThrownBy(() -> mavenLocalRepository.addPlugin(project, plugin)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddProperty() throws Exception {
    Project project = tmpProjectWithPomXml();

    mavenLocalRepository.addProperty(project, "testcontainers", "1.16.0");

    assertFileContent(project, "pom.xml", "    <testcontainers.version>1.16.0</testcontainers.version>");
  }

  @Test
  void shouldNotAddProperty() {
    Project project = tmpProject();

    assertThatThrownBy(() -> mavenLocalRepository.addProperty(project, "testcontainers", "1.16.0"))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldInit() {
    Project project = tmpProject();

    mavenLocalRepository.init(project);

    assertFilesMaven(project);
    assertFileContent(project, "pom.xml", "<name>jhipster</name>");
    assertFileContent(project, "pom.xml", "<description>JHipster Project</description>");
  }

  @Test
  void shouldAddPomXml() {
    Project project = tmpProject();

    mavenLocalRepository.addPomXml(project);

    assertFilesPomXml(project);
    assertFileContent(project, "pom.xml", "<name>jhipster</name>");
    assertFileContent(project, "pom.xml", "<description>JHipster Project</description>");
  }

  @Test
  void shouldAddMavenWrapper() {
    Project project = tmpProject();

    mavenLocalRepository.addMavenWrapper(project);

    assertFilesMavenWrapper(project);
  }
}
