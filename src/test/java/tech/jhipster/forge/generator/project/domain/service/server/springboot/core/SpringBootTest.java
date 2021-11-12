package tech.jhipster.forge.generator.project.domain.service.server.springboot.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.jhipster.forge.UnitTest;

@UnitTest
class SpringBootTest {

  @Test
  void shouldGetVersion() {
    Assertions.assertThat(SpringBoot.getVersion()).isEqualTo(SpringBoot.SPRING_BOOT_VERSION);
  }
}
