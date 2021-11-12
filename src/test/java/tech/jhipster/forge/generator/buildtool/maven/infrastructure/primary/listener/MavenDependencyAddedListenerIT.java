package tech.jhipster.forge.generator.buildtool.maven.infrastructure.primary.listener;

import static tech.jhipster.forge.TestUtils.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.forge.generator.buildtool.generic.domain.DependencyAdded;
import tech.jhipster.forge.generator.project.domain.model.Project;

@IntegrationTest
class MavenDependencyAddedListenerIT {

  @Autowired
  ApplicationEventPublisher publisher;

  @Test
  void shouldAddDependency() throws Exception {
    // Given
    Project project = tmpProjectWithPomXml();
    Dependency dependency = keycloakAdminClientDependency();
    DependencyAdded dependencyAdded = DependencyAdded.of(project, dependency);

    // When
    publisher.publishEvent(dependencyAdded);

    // Then
    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.keycloak</groupId>",
        "<artifactId>keycloak-admin-client</artifactId>",
        "<version>${keycloak.version}</version>",
        "</dependency>"
      )
    );
  }

  @Test
  void shouldNotAddDependency() {
    // Given
    Project project = tmpProject();
    Dependency dependency = keycloakAdminClientDependency();
    DependencyAdded dependencyAdded = DependencyAdded.of(project, dependency);

    // When
    publisher.publishEvent(dependencyAdded);

    // Then
    assertFileNotExist(project, "pom.xml");
  }

  private Dependency keycloakAdminClientDependency() {
    return Dependency.builder().groupId("org.keycloak").artifactId("keycloak-admin-client").version("\\${keycloak.version}").build();
  }
}
