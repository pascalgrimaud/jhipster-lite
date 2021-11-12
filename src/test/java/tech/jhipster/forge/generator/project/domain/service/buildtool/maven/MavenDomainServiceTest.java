package tech.jhipster.forge.generator.project.domain.service.buildtool.maven;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static tech.jhipster.forge.TestUtils.tmpProject;
import static tech.jhipster.forge.TestUtils.tmpProjectWithPomXml;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.common.domain.FileUtils;
import tech.jhipster.forge.error.domain.GeneratorException;
import tech.jhipster.forge.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.forge.generator.buildtool.generic.domain.Parent;
import tech.jhipster.forge.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.forge.generator.project.domain.model.Project;

@UnitTest
@ExtendWith(MockitoExtension.class)
class MavenDomainServiceTest {

  @Mock
  MavenRepository mavenRepository;

  @InjectMocks
  MavenDomainService mavenDomainService;

  @Test
  void shouldAddParent() throws Exception {
    Project project = tmpProjectWithPomXml();
    Parent parent = Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("2.5.3").build();

    mavenDomainService.addParent(project, parent);

    verify(mavenRepository).addParent(project, parent);
  }

  @Test
  void shouldNotAddParentWhenNoPomXml() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(project.getFolder());
    Parent parent = Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("2.5.3").build();

    assertThatThrownBy(() -> mavenDomainService.addParent(project, parent)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddDependency() throws Exception {
    Project project = tmpProjectWithPomXml();
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();

    mavenDomainService.addDependency(project, dependency);

    verify(mavenRepository).addDependency(project, dependency);
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

    mavenDomainService.addDependency(project, dependency);

    verify(mavenRepository).addDependency(project, dependency);
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

    mavenDomainService.addDependency(project, dependency, List.of(dependencyToExclude));

    verify(mavenRepository).addDependency(project, dependency, List.of(dependencyToExclude));
  }

  @Test
  void shouldNotAddDependencyWhenNoPomXml() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(project.getFolder());
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();

    assertThatThrownBy(() -> mavenDomainService.addDependency(project, dependency)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotAddDependencyWithExclusionsWhenNoPomXml() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(project.getFolder());
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();
    Dependency dependencyToExclude = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-tomcat")
      .build();

    assertThatThrownBy(() -> mavenDomainService.addDependency(project, dependency, List.of(dependencyToExclude)))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddPlugin() throws Exception {
    Project project = tmpProjectWithPomXml();
    Plugin plugin = Plugin.builder().groupId("org.springframework.boot").artifactId("spring-boot-maven-plugin").build();

    mavenDomainService.addPlugin(project, plugin);

    verify(mavenRepository).addPlugin(project, plugin);
  }

  @Test
  void shouldNotAddPluginWhenNoPomXml() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(project.getFolder());
    Plugin plugin = Plugin.builder().groupId("org.springframework.boot").artifactId("spring-boot-maven-plugin").build();

    assertThatThrownBy(() -> mavenDomainService.addPlugin(project, plugin)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddProperty() throws Exception {
    Project project = tmpProjectWithPomXml();

    mavenDomainService.addProperty(project, "testcontainers", "1.16.0");

    verify(mavenRepository).addProperty(project, "testcontainers", "1.16.0");
  }

  @Test
  void shouldNotAddPropertyWhenNoPomXml() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(project.getFolder());

    assertThatThrownBy(() -> mavenDomainService.addProperty(project, "testcontainers", "1.16.0"))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldInit() {
    Project project = tmpProject();

    mavenDomainService.init(project);

    verify(mavenRepository).init(project);
  }

  @Test
  void shouldAddPomXml() {
    Project project = tmpProject();

    mavenDomainService.addPomXml(project);

    verify(mavenRepository).addPomXml(project);
  }
}
