package tech.jhipster.forge.generator.buildtool.generic.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static tech.jhipster.forge.TestUtils.tmpProject;

import org.junit.jupiter.api.Test;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.error.domain.MissingMandatoryValueException;
import tech.jhipster.forge.generator.project.domain.model.Project;

@UnitTest
class PluginAddedTest {

  @Test
  void shouldBuild() {
    Project project = tmpProject();
    Plugin plugin = getPlugin();

    PluginAdded pluginAdded = PluginAdded.of(project, plugin);

    assertThat(pluginAdded.project()).usingRecursiveComparison().isEqualTo(project);
    assertThat(pluginAdded.plugin()).usingRecursiveComparison().isEqualTo(plugin);
  }

  @Test
  void shouldNotBuildWithNullProject() {
    assertThatThrownBy(() -> PluginAdded.of(null, getPlugin()))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotBuildWithNullPlugin() {
    assertThatThrownBy(() -> PluginAdded.of(tmpProject(), null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("plugin");
  }

  private Plugin getPlugin() {
    return Plugin.builder().groupId("org.springframework.boot").artifactId("spring-boot-maven-plugin").build();
  }
}
