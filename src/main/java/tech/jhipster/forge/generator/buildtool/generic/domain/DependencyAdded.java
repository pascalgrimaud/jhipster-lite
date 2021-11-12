package tech.jhipster.forge.generator.buildtool.generic.domain;

import java.util.List;
import tech.jhipster.forge.error.domain.Assert;
import tech.jhipster.forge.generator.project.domain.model.Project;

public record DependencyAdded(Project project, Dependency dependency, List<Dependency> exclusions) {
  public DependencyAdded {
    Assert.notNull("project", project);
    Assert.notNull("dependency", dependency);
    Assert.notNull("exclusions", exclusions);
  }

  public static DependencyAdded of(Project project, Dependency dependency, List<Dependency> exclusions) {
    return new DependencyAdded(project, dependency, exclusions);
  }

  public static DependencyAdded of(Project project, Dependency dependency) {
    return new DependencyAdded(project, dependency, List.of());
  }
}
