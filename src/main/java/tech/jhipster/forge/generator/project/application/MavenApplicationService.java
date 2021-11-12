package tech.jhipster.forge.generator.project.application;

import java.util.List;
import org.springframework.stereotype.Service;
import tech.jhipster.forge.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.forge.generator.buildtool.generic.domain.Parent;
import tech.jhipster.forge.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.service.buildtool.maven.MavenService;

@Service
public class MavenApplicationService {

  private final MavenService mavenService;

  public MavenApplicationService(MavenService mavenService) {
    this.mavenService = mavenService;
  }

  public void addParent(Project project, Parent parent) {
    mavenService.addParent(project, parent);
  }

  public void addDependency(Project project, Dependency dependency) {
    mavenService.addDependency(project, dependency);
  }

  public void addDependency(Project project, Dependency dependency, List<Dependency> exclusions) {
    mavenService.addDependency(project, dependency, exclusions);
  }

  public void addPlugin(Project project, Plugin plugin) {
    mavenService.addPlugin(project, plugin);
  }

  public void addProperty(Project project, String key, String version) {
    mavenService.addProperty(project, key, version);
  }

  public void init(Project project) {
    mavenService.init(project);
  }

  public void addPomXml(Project project) {
    mavenService.addPomXml(project);
  }
}
