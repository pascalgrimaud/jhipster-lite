package tech.jhipster.forge.generator.buildtool.maven.infrastructure.primary.listener;

import static tech.jhipster.forge.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.generator.buildtool.generic.domain.PropertyAdded;
import tech.jhipster.forge.generator.project.domain.model.Project;

@IntegrationTest
class MavenPropertyAddedListenerIT {

  @Autowired
  ApplicationEventPublisher publisher;

  @Test
  void shouldAddProperty() throws Exception {
    Project project = tmpProjectWithPomXml();
    PropertyAdded propertyAdded = PropertyAdded.of(project, "testcontainers", "1.16.0");

    publisher.publishEvent(propertyAdded);

    assertFileContent(project, "pom.xml", "<testcontainers.version>1.16.0</testcontainers.version>");
  }

  @Test
  void shouldNotAddProperty() {
    Project project = tmpProject();
    PropertyAdded propertyAdded = PropertyAdded.of(project, "testcontainers", "1.16.0");

    publisher.publishEvent(propertyAdded);

    assertFileNotExist(project, "pom.xml");
  }
}
