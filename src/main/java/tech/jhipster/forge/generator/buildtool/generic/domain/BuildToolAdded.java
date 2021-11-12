package tech.jhipster.forge.generator.buildtool.generic.domain;

import tech.jhipster.forge.error.domain.Assert;
import tech.jhipster.forge.generator.project.domain.BuildToolType;
import tech.jhipster.forge.generator.project.domain.Project;

public record BuildToolAdded(Project project, BuildToolType buildTool) {
  public BuildToolAdded {
    Assert.notNull("project", project);
    Assert.notNull("buildTool", buildTool);
  }

  public static BuildToolAdded of(Project project, BuildToolType buildToolType) {
    return new BuildToolAdded(project, buildToolType);
  }
}
