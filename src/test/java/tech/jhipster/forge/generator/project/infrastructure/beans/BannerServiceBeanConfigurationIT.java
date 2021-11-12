package tech.jhipster.forge.generator.project.infrastructure.beans;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.banner.BannerDomainService;

@IntegrationTest
class BannerServiceBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("bannerService")).isNotNull().isInstanceOf(BannerDomainService.class);
  }
}
