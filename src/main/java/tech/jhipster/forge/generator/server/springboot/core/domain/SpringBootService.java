package tech.jhipster.forge.generator.server.springboot.core.domain;

import tech.jhipster.forge.generator.project.domain.model.Project;

public interface SpringBootService {
  void init(Project project);

  void addSpringBootParent(Project project);
  void addSpringBootDependencies(Project project);
  void addSpringBootMavenPlugin(Project project);
  void addMainApp(Project project);
  void addApplicationProperties(Project project);
  void addApplicationTestProperties(Project project);
}
