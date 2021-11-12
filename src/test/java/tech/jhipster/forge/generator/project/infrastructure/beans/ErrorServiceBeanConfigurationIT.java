package tech.jhipster.forge.generator.project.infrastructure.beans;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.generator.project.domain.service.server.javatool.error.ErrorDomainService;

@IntegrationTest
class ErrorServiceBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("errorService")).isNotNull().isInstanceOf(ErrorDomainService.class);
  }
}
