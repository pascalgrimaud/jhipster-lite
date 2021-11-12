package tech.jhipster.forge.generator.project.domain.service.init;

import tech.jhipster.forge.generator.project.domain.model.Project;

public interface InitRepository {
  void addPackageJson(Project project);
  void addReadme(Project project);
  void addGitConfiguration(Project project);
  void addEditorConfiguration(Project project);
  void addPrettier(Project project);
}
