package tech.jhipster.forge.generator.server.springboot.database.postgresql.infrastructure.beans;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.generator.project.infrastructure.secondary.server.springboot.database.PostgresqlDomainService;

@IntegrationTest
class PostgresqlServiceBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("postgresqlService")).isNotNull().isInstanceOf(PostgresqlDomainService.class);
  }
}
