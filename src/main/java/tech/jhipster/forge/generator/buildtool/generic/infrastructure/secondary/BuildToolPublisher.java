package tech.jhipster.forge.generator.buildtool.generic.infrastructure.secondary;

import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import tech.jhipster.forge.generator.buildtool.generic.domain.*;
import tech.jhipster.forge.generator.project.domain.BuildToolType;
import tech.jhipster.forge.generator.project.domain.Project;

@Component
public class BuildToolPublisher implements BuildToolRepository {

  private final ApplicationEventPublisher publisher;

  public BuildToolPublisher(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  @Override
  public void addParent(Project project, Parent parent) {
    project.checkBuildTool();

    publisher.publishEvent(ParentAdded.of(project, parent));
  }

  @Override
  public void addDependency(Project project, Dependency dependency) {
    project.checkBuildTool();

    addDependency(project, dependency, List.of());
  }

  @Override
  public void addDependency(Project project, Dependency dependency, List<Dependency> exclusions) {
    project.checkBuildTool();

    publisher.publishEvent(DependencyAdded.of(project, dependency, exclusions));
  }

  @Override
  public void addPlugin(Project project, Plugin plugin) {
    project.checkBuildTool();

    publisher.publishEvent(PluginAdded.of(project, plugin));
  }

  @Override
  public void addProperty(Project project, String key, String version) {
    project.checkBuildTool();

    publisher.publishEvent(PropertyAdded.of(project, key, version));
  }

  @Override
  public void init(Project project, BuildToolType buildTool) {
    publisher.publishEvent(BuildToolAdded.of(project, buildTool));
  }
}
