package tech.jhipster.forge.generator.buildtool.generic.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.forge.TestUtils.tmpProject;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.error.domain.MissingMandatoryValueException;
import tech.jhipster.forge.generator.project.domain.model.Project;

@UnitTest
class DependencyAddedTest {

  @Test
  void shouldBuildWithoutExclusions() {
    Project project = tmpProject();
    Dependency dependency = getDependency();
    DependencyAdded dependencyAdded = DependencyAdded.of(project, dependency);

    assertThat(dependencyAdded.project()).usingRecursiveComparison().isEqualTo(project);
    assertThat(dependencyAdded.dependency()).usingRecursiveComparison().isEqualTo(dependency);
    assertThat(dependencyAdded.exclusions()).isEmpty();
  }

  @Test
  void shouldBuildWithExclusions() {
    Project project = tmpProject();
    Dependency dependency = getDependency();
    List<Dependency> exclusions = getExclusions();
    DependencyAdded dependencyAdded = DependencyAdded.of(project, dependency, exclusions);

    assertThat(dependencyAdded.project()).usingRecursiveComparison().isEqualTo(project);
    assertThat(dependencyAdded.dependency()).usingRecursiveComparison().isEqualTo(dependency);
    assertThat(dependencyAdded.exclusions()).usingRecursiveComparison().isEqualTo(exclusions);
  }

  @Test
  void shouldNotBuildWithNullProject() {
    assertThatThrownBy(() -> DependencyAdded.of(null, getDependency(), getExclusions()))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotBuildWithNullDependency() {
    assertThatThrownBy(() -> DependencyAdded.of(tmpProject(), null, getExclusions()))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("dependency");
  }

  @Test
  void shouldNotBuildWithNullExclusions() {
    assertThatThrownBy(() -> DependencyAdded.of(tmpProject(), getDependency(), null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("exclusions");
  }

  private Dependency getDependency() {
    return Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-web").build();
  }

  private List<Dependency> getExclusions() {
    return List.of(Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-tomcat").build());
  }
}
