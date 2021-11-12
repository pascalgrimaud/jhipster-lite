package tech.jhipster.forge.generator.buildtool.generic.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.forge.TestUtils.tmpProject;

import org.junit.jupiter.api.Test;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.error.domain.MissingMandatoryValueException;
import tech.jhipster.forge.generator.project.domain.BuildToolType;
import tech.jhipster.forge.generator.project.domain.Project;

@UnitTest
class BuildToolAddedTest {

  @Test
  void shoudlBuild() {
    Project project = tmpProject();
    BuildToolType buildToolType = BuildToolType.MAVEN;
    BuildToolAdded buildToolAdded = BuildToolAdded.of(project, buildToolType);

    assertThat(buildToolAdded.project()).usingRecursiveComparison().isEqualTo(project);
    assertThat(buildToolAdded.buildTool()).isEqualTo(BuildToolType.MAVEN);
  }

  @Test
  void shouldNotBuildWithNullProject() {
    assertThatThrownBy(() -> BuildToolAdded.of(null, BuildToolType.MAVEN))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotBuildWithNullBuildTool() {
    assertThatThrownBy(() -> BuildToolAdded.of(tmpProject(), null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("buildTool");
  }
}
