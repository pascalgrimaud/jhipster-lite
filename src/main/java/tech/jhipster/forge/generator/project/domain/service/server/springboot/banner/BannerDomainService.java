package tech.jhipster.forge.generator.project.domain.service.server.springboot.banner;

import static tech.jhipster.forge.generator.project.domain.model.ServerFrameworkType.SPRING;

import tech.jhipster.forge.error.domain.GeneratorException;
import tech.jhipster.forge.generator.project.domain.model.Project;

public class BannerDomainService implements BannerService {

  private final BannerRepository bannerRepository;

  public BannerDomainService(BannerRepository bannerRepository) {
    this.bannerRepository = bannerRepository;
  }

  @Override
  public void addBannerJHipsterV7(Project project) {
    assertSpringProject(project);

    bannerRepository.addBannerJHipsterV7(project);
  }

  @Override
  public void addBannerJHipsterV7React(Project project) {
    assertSpringProject(project);
    bannerRepository.addBannerJHipsterV7React(project);
  }

  @Override
  public void addBannerJHipsterV7Vue(Project project) {
    assertSpringProject(project);
    bannerRepository.addBannerJHipsterV7Vue(project);
  }

  @Override
  public void addBannerJHipsterV2(Project project) {
    assertSpringProject(project);
    bannerRepository.addBannerJHipsterV2(project);
  }

  @Override
  public void addBannerJHipsterV3(Project project) {
    assertSpringProject(project);
    bannerRepository.addBannerJHipsterV3(project);
  }

  private void assertSpringProject(Project project) {
    if (SPRING != project.getServer().orElse(null)) {
      throw new GeneratorException("Not Spring project");
    }
  }
}
