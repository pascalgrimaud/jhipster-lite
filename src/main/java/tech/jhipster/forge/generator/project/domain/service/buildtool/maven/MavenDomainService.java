package tech.jhipster.forge.generator.project.domain.service.buildtool.maven;

import java.util.List;
import tech.jhipster.forge.error.domain.GeneratorException;
import tech.jhipster.forge.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.forge.generator.buildtool.generic.domain.Parent;
import tech.jhipster.forge.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.forge.generator.project.domain.model.Project;

public class MavenDomainService implements MavenService {

  private final MavenRepository mavenRepository;

  public MavenDomainService(MavenRepository mavenRepository) {
    this.mavenRepository = mavenRepository;
  }

  @Override
  public void addParent(Project project, Parent parent) {
    if (!project.isMavenProject()) {
      throw new GeneratorException("No maven");
    }
    mavenRepository.addParent(project, parent);
  }

  @Override
  public void addDependency(Project project, Dependency dependency) {
    if (!project.isMavenProject()) {
      throw new GeneratorException("No maven");
    }
    mavenRepository.addDependency(project, dependency);
  }

  @Override
  public void addDependency(Project project, Dependency dependency, List<Dependency> exclusions) {
    if (!project.isMavenProject()) {
      throw new GeneratorException("No maven");
    }
    mavenRepository.addDependency(project, dependency, exclusions);
  }

  @Override
  public void addPlugin(Project project, Plugin plugin) {
    if (!project.isMavenProject()) {
      throw new GeneratorException("No maven");
    }
    mavenRepository.addPlugin(project, plugin);
  }

  @Override
  public void addProperty(Project project, String key, String version) {
    if (!project.isMavenProject()) {
      throw new GeneratorException("No maven");
    }
    mavenRepository.addProperty(project, key, version);
  }

  @Override
  public void init(Project project) {
    mavenRepository.init(project);
  }

  @Override
  public void addPomXml(Project project) {
    mavenRepository.addPomXml(project);
  }
}
