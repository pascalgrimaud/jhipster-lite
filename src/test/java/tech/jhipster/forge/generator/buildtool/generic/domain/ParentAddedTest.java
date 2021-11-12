package tech.jhipster.forge.generator.buildtool.generic.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.forge.TestUtils.tmpProject;

import org.junit.jupiter.api.Test;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.error.domain.MissingMandatoryValueException;
import tech.jhipster.forge.generator.project.domain.model.Project;

@UnitTest
class ParentAddedTest {

  @Test
  void shouldBuild() {
    Project project = tmpProject();
    Parent parent = getParent();
    ParentAdded parentAdded = ParentAdded.of(project, parent);

    assertThat(parentAdded.project()).usingRecursiveComparison().isEqualTo(project);
    assertThat(parentAdded.parent()).usingRecursiveComparison().isEqualTo(parent);
  }

  @Test
  void shouldNotBuildWithNullProject() {
    assertThatThrownBy(() -> ParentAdded.of(null, getParent()))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotBuildWithNullParent() {
    assertThatThrownBy(() -> ParentAdded.of(tmpProject(), null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("parent");
  }

  private Parent getParent() {
    return Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("2.5.3").build();
  }
}
