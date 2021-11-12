package tech.jhipster.forge.generator.project.domain.service.server.springboot.core;

import tech.jhipster.forge.generator.project.domain.model.Project;

public interface SpringBootRepository {
  void init(Project project);

  void addSpringBootParent(Project project);
  void addSpringBootDependencies(Project project);
  void addSpringBootMavenPlugin(Project project);
  void addMainApp(Project project);
  void addApplicationProperties(Project project);
  void addApplicationTestProperties(Project project);
}
