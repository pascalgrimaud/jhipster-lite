package tech.jhipster.forge.generator.buildtool.generic.domain;

import tech.jhipster.forge.error.domain.Assert;
import tech.jhipster.forge.generator.project.domain.model.Project;

public record ParentAdded(Project project, Parent parent) {
  public ParentAdded {
    Assert.notNull("project", project);
    Assert.notNull("parent", parent);
  }

  public static ParentAdded of(Project project, Parent parent) {
    return new ParentAdded(project, parent);
  }
}
