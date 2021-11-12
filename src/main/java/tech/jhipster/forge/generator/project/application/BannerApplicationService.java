package tech.jhipster.forge.generator.project.application;

import org.springframework.stereotype.Component;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.banner.BannerRepository;

@Component
public class BannerApplicationService {

  private final BannerRepository bannerRepository;

  public BannerApplicationService(BannerRepository bannerRepository) {
    this.bannerRepository = bannerRepository;
  }

  public void addBannerJHipsterV7(Project project) {
    bannerRepository.addBannerJHipsterV7(project);
  }

  public void addBannerJHipsterV7React(Project project) {
    bannerRepository.addBannerJHipsterV7React(project);
  }

  public void addBannerJHipsterV7Vue(Project project) {
    bannerRepository.addBannerJHipsterV7Vue(project);
  }

  public void addBannerJHipsterV2(Project project) {
    bannerRepository.addBannerJHipsterV2(project);
  }

  public void addBannerJHipsterV3(Project project) {
    bannerRepository.addBannerJHipsterV3(project);
  }
}
