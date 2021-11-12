package tech.jhipster.forge.generator.server.springboot.properties.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static tech.jhipster.forge.TestUtils.tmpProject;
import static tech.jhipster.forge.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.common.domain.FileUtils.getPathOf;
import static tech.jhipster.forge.generator.project.domain.model.Constants.MAIN_RESOURCES;
import static tech.jhipster.forge.generator.project.domain.model.Constants.TEST_RESOURCES;
import static tech.jhipster.forge.generator.server.springboot.core.domain.SpringBoot.APPLICATION_PROPERTIES;

import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.common.domain.FileUtils;
import tech.jhipster.forge.error.domain.GeneratorException;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SpringBootPropertiesDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  SpringBootPropertiesDomainService springBootPropertiesDomainService;

  @Test
  void shouldAddProperties() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(getPath(project.getFolder(), MAIN_RESOURCES, "config"));
    Files.copy(
      getPathOf(TEST_RESOURCES, "template/server/springboot/core/application.test.properties"),
      getPathOf(project.getFolder(), MAIN_RESOURCES, "config", APPLICATION_PROPERTIES)
    );

    springBootPropertiesDomainService.addProperties(project, "server.port", 8080);

    verify(projectRepository).write(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldNotAddProperties() {
    Project project = tmpProject();

    assertThatThrownBy(() -> springBootPropertiesDomainService.addProperties(project, "server.port", 8080))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddPropertiesTest() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(getPath(project.getFolder(), TEST_RESOURCES, "config"));
    Files.copy(
      getPathOf(TEST_RESOURCES, "template/server/springboot/core/application.test.properties"),
      getPathOf(project.getFolder(), TEST_RESOURCES, "config", APPLICATION_PROPERTIES)
    );

    springBootPropertiesDomainService.addPropertiesTest(project, "server.port", 8080);

    verify(projectRepository).write(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldNotAddPropertiesTest() {
    Project project = tmpProject();

    assertThatThrownBy(() -> springBootPropertiesDomainService.addPropertiesTest(project, "server.port", 8080))
      .isExactlyInstanceOf(GeneratorException.class);
  }
}
