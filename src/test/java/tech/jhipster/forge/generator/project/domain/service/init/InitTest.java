package tech.jhipster.forge.generator.project.domain.service.init;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.forge.UnitTest;

@UnitTest
class InitTest {

  @Test
  void shouldGetNodeVersion() {
    assertThat(Init.getNodeVersion()).isEqualTo(Init.NODE_VERSION);
  }
}
