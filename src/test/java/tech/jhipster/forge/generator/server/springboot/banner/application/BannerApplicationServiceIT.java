package tech.jhipster.forge.generator.server.springboot.banner.application;

import static tech.jhipster.forge.TestUtils.*;
import static tech.jhipster.forge.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.generator.project.domain.model.Constants.MAIN_RESOURCES;
import static tech.jhipster.forge.generator.project.domain.model.ServerFrameworkType.SPRING;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.generator.project.application.BannerApplicationService;
import tech.jhipster.forge.generator.project.domain.model.Project;

@IntegrationTest
class BannerApplicationServiceIT {

  @Autowired
  BannerApplicationService bannerApplicationService;

  @Test
  @DisplayName("should add banner JHipster v7")
  void shouldAddBannerJHipsterV7() {
    Project project = springProject();

    bannerApplicationService.addBannerJHipsterV7(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }

  @Test
  @DisplayName("should add banner JHipster v7 for React")
  void shouldAddBannerJHipsterV7React() {
    Project project = springProject();

    bannerApplicationService.addBannerJHipsterV7React(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }

  @Test
  @DisplayName("should add banner JHipster v7 for Vue")
  void shouldAddBannerJHipsterV7Vue() {
    Project project = springProject();

    bannerApplicationService.addBannerJHipsterV7Vue(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }

  @Test
  @DisplayName("should add banner JHipster v2")
  void shouldAddBannerJHipsterV2() {
    Project project = springProject();

    bannerApplicationService.addBannerJHipsterV2(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }

  @Test
  @DisplayName("should add banner JHipster v3")
  void shouldAddBannerJHipsterV3() {
    Project project = springProject();

    bannerApplicationService.addBannerJHipsterV3(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }

  private Project springProject() {
    return tmpProjectBuilder().server(SPRING).build();
  }
}
