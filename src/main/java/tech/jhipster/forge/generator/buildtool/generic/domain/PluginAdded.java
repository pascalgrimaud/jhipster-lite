package tech.jhipster.forge.generator.buildtool.generic.domain;

import tech.jhipster.forge.error.domain.Assert;
import tech.jhipster.forge.generator.project.domain.model.Project;

public record PluginAdded(Project project, Plugin plugin) {
  public PluginAdded {
    Assert.notNull("project", project);
    Assert.notNull("plugin", plugin);
  }

  public static PluginAdded of(Project project, Plugin plugin) {
    return new PluginAdded(project, plugin);
  }
}
