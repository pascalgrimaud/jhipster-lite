package tech.jhipster.forge.generator.project.infrastructure.secondary.server.springboot.banner;

import static tech.jhipster.forge.generator.project.domain.model.Constants.MAIN_RESOURCES;

import org.springframework.stereotype.Repository;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.banner.BannerRepository;

@Repository
public class BannerLocalRepository implements BannerRepository {

  public static final String SOURCE = "server/springboot/banner";

  private final ProjectRepository projectRepository;

  public BannerLocalRepository(ProjectRepository projectRepository) {
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
