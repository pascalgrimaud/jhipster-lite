package tech.jhipster.forge.generator.server.springboot.database.postgresql.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.database.postgresql.Postgresql;

@UnitTest
class PostgresqlTest {

  @Test
  void shouldGetTestcontainersVersion() {
    assertThat(Postgresql.getTestcontainersVersion()).isEqualTo("1.16.0");
  }
}
