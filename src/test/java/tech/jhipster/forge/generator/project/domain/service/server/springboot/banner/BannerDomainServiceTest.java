package tech.jhipster.forge.generator.project.domain.service.server.springboot.banner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static tech.jhipster.forge.TestUtils.tmpProject;
import static tech.jhipster.forge.TestUtils.tmpProjectBuilder;
import static tech.jhipster.forge.generator.project.domain.model.ServerFrameworkType.SPRING;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.error.domain.GeneratorException;
import tech.jhipster.forge.generator.project.domain.model.Project;

@UnitTest
@ExtendWith(MockitoExtension.class)
class BannerDomainServiceTest {

  @Mock
  BannerRepository bannerRepository;

  @InjectMocks
  BannerDomainService bannerDomainService;

  @Test
  @DisplayName("should add banner JHipster v7")
  void shouldAddBannerJHipsterV7() {
    Project project = tmpProjectBuilder().server(SPRING).build();
    bannerDomainService.addBannerJHipsterV7(project);
    verify(bannerRepository).addBannerJHipsterV7(project);
  }

  @Test
  @DisplayName("should not add banner JHipster v7")
  void shouldNotAddBannerJHipsterV7() {
    Project project = tmpProject();
    assertThatThrownBy(() -> bannerDomainService.addBannerJHipsterV7(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  @DisplayName("should add banner JHipster v7 for React")
  void shouldAddBannerJHipsterV7React() {
    Project project = tmpProjectBuilder().server(SPRING).build();
    bannerDomainService.addBannerJHipsterV7React(project);
    verify(bannerRepository).addBannerJHipsterV7React(project);
  }

  @Test
  @DisplayName("should not add banner JHipster v7 for React")
  void shouldNotAddBannerJHipsterV7React() {
    Project project = tmpProject();
    assertThatThrownBy(() -> bannerDomainService.addBannerJHipsterV7React(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  @DisplayName("should add banner JHipster v7 for Vue")
  void shouldAddBannerJHipsterV7Vue() {
    Project project = tmpProjectBuilder().server(SPRING).build();
    bannerDomainService.addBannerJHipsterV7Vue(project);
    verify(bannerRepository).addBannerJHipsterV7Vue(project);
  }

  @Test
  @DisplayName("should not add banner JHipster v7 for Vue")
  void shouldNotAddBannerJHipsterV7Vue() {
    Project project = tmpProject();
    assertThatThrownBy(() -> bannerDomainService.addBannerJHipsterV7Vue(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  @DisplayName("should add banner JHipster v2")
  void shouldAddBannerJHipsterV2() {
    Project project = tmpProjectBuilder().server(SPRING).build();
    bannerDomainService.addBannerJHipsterV2(project);
    verify(bannerRepository).addBannerJHipsterV2(project);
  }

  @Test
  @DisplayName("should not add banner JHipster v2")
  void shouldNotAddBannerJHipsterV2() {
    Project project = tmpProject();
    assertThatThrownBy(() -> bannerDomainService.addBannerJHipsterV2(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  @DisplayName("should add banner JHipster v3")
  void shouldAddBannerJHipsterV3() {
    Project project = tmpProjectBuilder().server(SPRING).build();
    bannerDomainService.addBannerJHipsterV3(project);
    verify(bannerRepository).addBannerJHipsterV3(project);
  }

  @Test
  @DisplayName("should not add banner JHipster v3")
  void shouldNotAddBannerJHipsterV3() {
    Project project = tmpProject();
    assertThatThrownBy(() -> bannerDomainService.addBannerJHipsterV3(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
