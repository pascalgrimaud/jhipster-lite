package tech.jhipster.forge.generator.project.application;

import org.springframework.stereotype.Component;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.banner.BannerService;

@Component
public class BannerApplicationService {

  private final BannerService bannerService;

  public BannerApplicationService(BannerService bannerService) {
    this.bannerService = bannerService;
  }

  public void addBannerJHipsterV7(Project project) {
    bannerService.addBannerJHipsterV7(project);
  }

  public void addBannerJHipsterV7React(Project project) {
    bannerService.addBannerJHipsterV7React(project);
  }

  public void addBannerJHipsterV7Vue(Project project) {
    bannerService.addBannerJHipsterV7Vue(project);
  }

  public void addBannerJHipsterV2(Project project) {
    bannerService.addBannerJHipsterV2(project);
  }

  public void addBannerJHipsterV3(Project project) {
    bannerService.addBannerJHipsterV3(project);
  }
}
