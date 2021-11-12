package tech.jhipster.forge.generator.project.infrastructure.beans;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.generator.project.domain.service.init.InitDomainService;

@IntegrationTest
class InitServiceBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("initService")).isNotNull().isInstanceOf(InitDomainService.class);
  }
}
