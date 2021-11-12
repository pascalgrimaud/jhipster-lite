package tech.jhipster.forge.generator.project.domain.service.server.springboot.core;

import tech.jhipster.forge.generator.project.domain.model.Project;

public class SpringBootDomainService implements SpringBootService {

  private final SpringBootRepository springBootRepository;

  public SpringBootDomainService(SpringBootRepository springBootRepository) {
    this.springBootRepository = springBootRepository;
  }

  @Override
  public void init(Project project) {
    project.checkBuildTool();

    springBootRepository.addSpringBootParent(project);
    springBootRepository.addSpringBootDependencies(project);
    springBootRepository.addSpringBootMavenPlugin(project);
    springBootRepository.addMainApp(project);
    springBootRepository.addApplicationProperties(project);
    springBootRepository.addApplicationTestProperties(project);
  }

  @Override
  public void addSpringBootParent(Project project) {
    project.checkBuildTool();
    springBootRepository.addSpringBootParent(project);
  }

  @Override
  public void addSpringBootDependencies(Project project) {
    project.checkBuildTool();
    springBootRepository.addSpringBootDependencies(project);
  }

  @Override
  public void addSpringBootMavenPlugin(Project project) {
    project.checkBuildTool();
    springBootRepository.addSpringBootMavenPlugin(project);
  }

  @Override
  public void addMainApp(Project project) {
    project.checkBuildTool();
    springBootRepository.addMainApp(project);
  }

  @Override
  public void addApplicationProperties(Project project) {
    project.checkBuildTool();
    springBootRepository.addApplicationProperties(project);
  }

  @Override
  public void addApplicationTestProperties(Project project) {
    project.checkBuildTool();
    springBootRepository.addApplicationTestProperties(project);
  }
}
