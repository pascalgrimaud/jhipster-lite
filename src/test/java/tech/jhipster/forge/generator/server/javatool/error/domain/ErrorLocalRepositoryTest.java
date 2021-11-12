package tech.jhipster.forge.generator.server.javatool.error.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static tech.jhipster.forge.TestUtils.tmpProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;
import tech.jhipster.forge.generator.project.infrastructure.secondary.server.javatool.error.ErrorLocalRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ErrorLocalRepositoryTest {

  @Mock
  ProjectRepository projectRepository;

  ErrorLocalRepository errorLocalRepository;

  @BeforeEach
  void setUp() {
    errorLocalRepository = new ErrorLocalRepository(projectRepository);
  }

  @Test
  void shouldInit() {
    Project project = tmpProject();

    errorLocalRepository.init(project);

    verify(projectRepository, times(6)).template(any(Project.class), anyString(), anyString(), anyString());
  }
}
