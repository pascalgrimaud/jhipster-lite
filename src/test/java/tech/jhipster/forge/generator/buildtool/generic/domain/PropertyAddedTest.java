package tech.jhipster.forge.generator.buildtool.generic.domain;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.forge.TestUtils.tmpProject;

import org.junit.jupiter.api.Test;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.error.domain.MissingMandatoryValueException;
import tech.jhipster.forge.generator.project.domain.model.Project;

@UnitTest
class PropertyAddedTest {

  @Test
  void shouldBuild() {
    Project project = tmpProject();

    PropertyAdded propertyAdded = PropertyAdded.of(project, "testcontainers", "1.16.0");

    assertThat(propertyAdded.project()).usingRecursiveComparison().isEqualTo(project);
    assertThat(propertyAdded.key()).isEqualTo("testcontainers");
    assertThat(propertyAdded.version()).isEqualTo("1.16.0");
  }

  @Test
  void shouldNotBuildWithNullProject() {
    assertThatThrownBy(() -> PropertyAdded.of(null, "testcontainers", "1.16.0"))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotBuildWithNullKey() {
    assertThatThrownBy(() -> PropertyAdded.of(tmpProject(), null, "1.16.0"))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("key");
  }

  @Test
  void shouldNotBuildWithBlankKey() {
    assertThatThrownBy(() -> PropertyAdded.of(tmpProject(), " ", "1.16.0"))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("key");
  }

  @Test
  void shouldNotBuildWithNullVersion() {
    assertThatThrownBy(() -> PropertyAdded.of(tmpProject(), "testcontainers", null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("version");
  }

  @Test
  void shouldNotBuildWithBlankVersion() {
    assertThatThrownBy(() -> PropertyAdded.of(tmpProject(), "testcontainers", " "))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("version");
  }
}
