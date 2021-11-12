package tech.jhipster.forge.generator.project.domain.service.server.javatool.error;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static tech.jhipster.forge.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.forge.generator.project.domain.model.LanguageType.JAVA;

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
class ErrorDomainServiceTest {

  @Mock
  ErrorRepository errorRepository;

  @InjectMocks
  ErrorDomainService errorDomainService;

  @Test
  void shouldInit() {
    Project project = Project.builder().folder(tmpDirForTest()).language(JAVA).build();

    errorDomainService.init(project);

    verify(errorRepository).init(project);
  }

  @Test
  void shouldNotInit() {
    Project project = Project.builder().folder(tmpDirForTest()).build();

    assertThatThrownBy(() -> errorDomainService.init(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
