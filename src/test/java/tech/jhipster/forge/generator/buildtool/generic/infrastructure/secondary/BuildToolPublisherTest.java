package tech.jhipster.forge.generator.buildtool.generic.infrastructure.secondary;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static tech.jhipster.forge.TestUtils.tmpProjectWithPomXml;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.common.domain.FileUtils;
import tech.jhipster.forge.generator.buildtool.generic.domain.*;
import tech.jhipster.forge.generator.project.domain.BuildToolType;
import tech.jhipster.forge.generator.project.domain.Project;

@UnitTest
@ExtendWith(SpringExtension.class)
class BuildToolPublisherTest {

  @Mock
  ApplicationEventPublisher publisher;

  @InjectMocks
  BuildToolPublisher buildToolPublisher;

  @Test
  void shouldAddParent() throws IOException {
    buildToolPublisher.addParent(tmpProjectWithPomXml(), getParent());

    verify(publisher).publishEvent(any(ParentAdded.class));
  }

  @Test
  void shouldAddDependency() throws IOException {
    buildToolPublisher.addDependency(tmpProjectWithPomXml(), getDependency());

    verify(publisher).publishEvent(any(DependencyAdded.class));
  }

  @Test
  void shouldAddDependencyWithExclusions() throws IOException {
    buildToolPublisher.addDependency(tmpProjectWithPomXml(), getDependency(), getExclusions());

    verify(publisher).publishEvent(any(DependencyAdded.class));
  }

  @Test
  void shouldAddPlugin() throws IOException {
    buildToolPublisher.addPlugin(tmpProjectWithPomXml(), getPlugin());

    verify(publisher).publishEvent(any(PluginAdded.class));
  }

  @Test
  void shouldAddProperty() throws IOException {
    buildToolPublisher.addProperty(tmpProjectWithPomXml(), "testcontainers", "1.16.0");

    verify(publisher).publishEvent(any(PropertyAdded.class));
  }

  @Test
  void shouldInit() {
    Project project = Project.builder().folder(FileUtils.tmpDirForTest()).build();
    buildToolPublisher.init(project, BuildToolType.MAVEN);

    verify(publisher).publishEvent(any(BuildToolAdded.class));
  }

  private Parent getParent() {
    return Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("2.5.3").build();
  }

  private Dependency getDependency() {
    return Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-web").build();
  }

  private List<Dependency> getExclusions() {
    return List.of(Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-tomcat").build());
  }

  private Plugin getPlugin() {
    return Plugin.builder().groupId("org.springframework.boot").artifactId("spring-boot-maven-plugin").build();
  }
}
