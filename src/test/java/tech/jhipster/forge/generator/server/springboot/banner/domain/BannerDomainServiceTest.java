package tech.jhipster.forge.generator.server.springboot.banner.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static tech.jhipster.forge.TestUtils.tmpProject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;
import tech.jhipster.forge.generator.project.infrastructure.secondary.server.springboot.banner.BannerLocalRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class BannerDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  BannerLocalRepository bannerDomainService;

  @Test
  void shouldAddBannerJHipsterV7() {
    bannerDomainService.addBannerJHipsterV7(tmpProject());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddBannerJHipsterV7React() {
    bannerDomainService.addBannerJHipsterV7React(tmpProject());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddBannerJHipsterV7Vue() {
    bannerDomainService.addBannerJHipsterV7Vue(tmpProject());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddBannerJHipsterV2() {
    bannerDomainService.addBannerJHipsterV2(tmpProject());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddBannerJHipsterV3() {
    bannerDomainService.addBannerJHipsterV3(tmpProject());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }
}
