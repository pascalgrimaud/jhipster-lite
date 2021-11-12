package tech.jhipster.forge.generator.server.springboot.banner.domain;

import static tech.jhipster.forge.generator.project.domain.model.Constants.MAIN_RESOURCES;

import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;

public class BannerDomainService implements BannerService {

  public static final String SOURCE = "server/springboot/banner";

  private final ProjectRepository projectRepository;

  public BannerDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addBannerJHipsterV7(Project project) {
    addBanner(project, "banner-jhipster-v7.txt");
  }

  @Override
  public void addBannerJHipsterV7React(Project project) {
    addBanner(project, "banner-jhipster-v7-react.txt");
  }

  @Override
  public void addBannerJHipsterV7Vue(Project project) {
    addBanner(project, "banner-jhipster-v7-vue.txt");
  }

  @Override
  public void addBannerJHipsterV2(Project project) {
    addBanner(project, "banner-jhipster-v2.txt");
  }

  @Override
  public void addBannerJHipsterV3(Project project) {
    addBanner(project, "banner-jhipster-v3.txt");
  }

  private void addBanner(Project project, String bannerFilename) {
    projectRepository.add(project, SOURCE, bannerFilename, MAIN_RESOURCES, "banner.txt");
  }
}
