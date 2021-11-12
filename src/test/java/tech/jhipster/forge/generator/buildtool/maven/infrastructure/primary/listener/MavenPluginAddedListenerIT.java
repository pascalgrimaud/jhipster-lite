package tech.jhipster.forge.generator.buildtool.maven.infrastructure.primary.listener;

import static tech.jhipster.forge.TestUtils.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.forge.generator.buildtool.generic.domain.PluginAdded;
import tech.jhipster.forge.generator.project.domain.model.Project;

@IntegrationTest
class MavenPluginAddedListenerIT {

  @Autowired
  ApplicationEventPublisher publisher;

  @Test
  void shouldAddPlugin() throws Exception {
    Project project = tmpProjectWithPomXml();
    Plugin plugin = springBootMavenPlugin();
    PluginAdded pluginAdded = PluginAdded.of(project, plugin);

    publisher.publishEvent(pluginAdded);

    assertFileContent(
      project,
      "pom.xml",
      List.of("<plugin>", "<groupId>org.springframework.boot</groupId>", "<artifactId>spring-boot-maven-plugin</artifactId>", "</plugin>")
    );
  }

  @Test
  void shouldNotAddPlugin() {
    Project project = tmpProject();
    Plugin plugin = springBootMavenPlugin();
    PluginAdded pluginAdded = PluginAdded.of(project, plugin);

    publisher.publishEvent(pluginAdded);

    assertFileNotExist(project, "pom.xml");
  }

  private Plugin springBootMavenPlugin() {
    return Plugin.builder().groupId("org.springframework.boot").artifactId("spring-boot-maven-plugin").build();
  }
}
