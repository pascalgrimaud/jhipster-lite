package tech.jhipster.forge.generator.project.infrastructure.secondary.server.springboot.banner;

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
class BannerLocalRepositoryIT {

  @Autowired
  BannerLocalRepository bannerLocalRepository;

  @Test
  @DisplayName("should add banner JHipster v7")
  void shouldAddBannerJHipsterV7() {
    Project project = tmpProject();

    bannerLocalRepository.addBannerJHipsterV7(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }

  @Test
  @DisplayName("should add banner JHipster v7 for React")
  void shouldAddBannerJHipsterV7React() {
    Project project = tmpProject();

    bannerLocalRepository.addBannerJHipsterV7React(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }

  @Test
  @DisplayName("should add banner JHipster v7 for Vue")
  void shouldAddBannerJHipsterV7Vue() {
    Project project = tmpProject();

    bannerLocalRepository.addBannerJHipsterV7Vue(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }

  @Test
  @DisplayName("should add banner JHipster v2")
  void shouldAddBannerJHipsterV2() {
    Project project = tmpProject();

    bannerLocalRepository.addBannerJHipsterV2(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }

  @Test
  @DisplayName("should add banner JHipster v3")
  void shouldAddBannerJHipsterV3() {
    Project project = tmpProject();

    bannerLocalRepository.addBannerJHipsterV3(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }
}
