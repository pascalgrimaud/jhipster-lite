package tech.jhipster.forge.generator.project.domain.service.init;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static tech.jhipster.forge.TestUtils.tmpProject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.generator.project.domain.model.Project;

@UnitTest
@ExtendWith(MockitoExtension.class)
class InitDomainServiceTest {

  @Mock
  private InitRepository initRepository;

  @InjectMocks
  private InitDomainService initDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProject();

    assertThatCode(() -> initDomainService.init(project)).doesNotThrowAnyException();

    verify(initRepository).addPackageJson(project);
    verify(initRepository).addReadme(project);
    verify(initRepository).addGitConfiguration(project);
    verify(initRepository).addEditorConfiguration(project);
    verify(initRepository).addPrettier(project);
  }
}
