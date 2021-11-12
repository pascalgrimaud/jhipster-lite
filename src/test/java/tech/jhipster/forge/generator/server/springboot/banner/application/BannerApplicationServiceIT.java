package tech.jhipster.forge.generator.server.springboot.banner.application;

import static tech.jhipster.forge.TestUtils.assertFileExist;
import static tech.jhipster.forge.TestUtils.tmpProject;
import static tech.jhipster.forge.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.generator.project.domain.model.Constants.MAIN_RESOURCES;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.generator.project.domain.model.Project;

@IntegrationTest
class BannerApplicationServiceIT {

  @Autowired
  BannerApplicationService bannerApplicationService;

  @Test
  @DisplayName("should add banner JHipster v7")
  void shouldAddBannerJHipsterV7() {
    Project project = tmpProject();

    bannerApplicationService.addBannerJHipsterV7(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }

  @Test
  @DisplayName("should add banner JHipster v7 for React")
  void shouldAddBannerJHipsterV7React() {
    Project project = tmpProject();

    bannerApplicationService.addBannerJHipsterV7React(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }

  @Test
  @DisplayName("should add banner JHipster v7 for Vue")
  void shouldAddBannerJHipsterV7Vue() {
    Project project = tmpProject();

    bannerApplicationService.addBannerJHipsterV7Vue(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }

  @Test
  @DisplayName("should add banner JHipster v2")
  void shouldAddBannerJHipsterV2() {
    Project project = tmpProject();

    bannerApplicationService.addBannerJHipsterV2(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }

  @Test
  @DisplayName("should add banner JHipster v3")
  void shouldAddBannerJHipsterV3() {
    Project project = tmpProject();

    bannerApplicationService.addBannerJHipsterV3(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }
}
