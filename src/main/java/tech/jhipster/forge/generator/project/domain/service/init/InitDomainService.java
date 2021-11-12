package tech.jhipster.forge.generator.project.domain.service.init;

import tech.jhipster.forge.generator.project.domain.model.Project;

public class InitDomainService implements InitService {

  private final InitRepository initRepository;

  public InitDomainService(InitRepository initRepository) {
    this.initRepository = initRepository;
  }

  @Override
  public void init(Project project) {
    initRepository.addPackageJson(project);
    initRepository.addReadme(project);
    initRepository.addGitConfiguration(project);
    initRepository.addEditorConfiguration(project);
    initRepository.addPrettier(project);
  }
}
