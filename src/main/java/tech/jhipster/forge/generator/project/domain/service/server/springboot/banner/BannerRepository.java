package tech.jhipster.forge.generator.project.domain.service.server.springboot.banner;

import tech.jhipster.forge.generator.project.domain.model.Project;

public interface BannerRepository {
  void addBannerJHipsterV7(Project project);
  void addBannerJHipsterV7React(Project project);
  void addBannerJHipsterV7Vue(Project project);
  void addBannerJHipsterV2(Project project);
  void addBannerJHipsterV3(Project project);
}
