package tech.jhipster.forge.generator.buildtool.maven.infrastructure.primary.listener;

import static tech.jhipster.forge.TestUtils.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.generator.buildtool.generic.domain.Parent;
import tech.jhipster.forge.generator.buildtool.generic.domain.ParentAdded;
import tech.jhipster.forge.generator.project.domain.model.Project;

@IntegrationTest
class MavenParentAddedListenerIT {

  @Autowired
  ApplicationEventPublisher publisher;

  @Test
  void shouldAddParent() throws Exception {
    Project project = tmpProjectWithPomXml();
    Parent parent = Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("2.5.3").build();
    ParentAdded parentAdded = ParentAdded.of(project, parent);

    publisher.publishEvent(parentAdded);

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
  void shouldNotAddParent() {
    Project project = tmpProject();
    Parent parent = Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("2.5.3").build();
    ParentAdded parentAdded = ParentAdded.of(project, parent);

    publisher.publishEvent(parentAdded);

    assertFileNotExist(project, "pom.xml");
  }
}
