package tech.jhipster.forge.generator.project.domain.service.server.springboot.core;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static tech.jhipster.forge.TestUtils.*;

import org.junit.jupiter.api.Nested;
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
class SpringBootDomainServiceTest {

  @Mock
  SpringBootRepository springBootRepository;

  @InjectMocks
  SpringBootDomainService springBootDomainService;

  @Nested
  class NoBuildTool {

    @Test
    void shouldNotInitWithoutBuildTool() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootDomainService.init(project)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotAddSpringBootParent() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootDomainService.addSpringBootParent(project)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddSpringBootDependencies() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootDomainService.addSpringBootDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddSpringBootMavenPlugin() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootDomainService.addSpringBootMavenPlugin(project)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddMainApp() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootDomainService.addMainApp(project)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddApplicationProperties() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootDomainService.addApplicationProperties(project)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddApplicationTestProperties() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootDomainService.addApplicationTestProperties(project)).isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Nested
  class Maven {

    @Test
    void shouldInit() {
      Project project = tmpProjectWithPomXml();

      springBootDomainService.init(project);

      verify(springBootRepository).init(project);
    }

    @Test
    void shouldAddSpringBootParent() {
      Project project = tmpProjectWithPomXml();

      springBootDomainService.addSpringBootParent(project);

      verify(springBootRepository).addSpringBootParent(project);
    }

    @Test
    void shouldAddSpringBootDependencies() {
      Project project = tmpProjectWithPomXml();

      springBootDomainService.addSpringBootDependencies(project);

      verify(springBootRepository).addSpringBootDependencies(project);
    }

    @Test
    void shouldAddSpringBootMavenPlugin() {
      Project project = tmpProjectWithPomXml();

      springBootDomainService.addSpringBootMavenPlugin(project);

      verify(springBootRepository).addSpringBootMavenPlugin(project);
    }

    @Test
    void shouldAddMainApp() {
      Project project = tmpProjectWithPomXml();

      springBootDomainService.addMainApp(project);

      verify(springBootRepository).addMainApp(project);
    }

    @Test
    void shouldAddApplicationProperties() {
      Project project = tmpProjectWithPomXml();

      springBootDomainService.addApplicationProperties(project);

      verify(springBootRepository).addApplicationProperties(project);
    }

    @Test
    void shouldAddApplicationTestProperties() {
      Project project = tmpProjectWithPomXml();

      springBootDomainService.addApplicationTestProperties(project);

      verify(springBootRepository).addApplicationTestProperties(project);
    }
  }

  @Nested
  class Gradle {

    @Test
    void shouldInitWithGradle() {
      Project project = tmpProjectWithBuildGradle();

      springBootDomainService.init(project);

      verify(springBootRepository).init(project);
    }

    @Test
    void shouldAddSpringBootParent() {
      Project project = tmpProjectWithBuildGradle();

      springBootDomainService.addSpringBootParent(project);

      verify(springBootRepository).addSpringBootParent(project);
    }

    @Test
    void shouldAddSpringBootDependencies() {
      Project project = tmpProjectWithBuildGradle();

      springBootDomainService.addSpringBootDependencies(project);

      verify(springBootRepository).addSpringBootDependencies(project);
    }

    @Test
    void shouldAddSpringBootMavenPlugin() {
      Project project = tmpProjectWithBuildGradle();

      springBootDomainService.addSpringBootMavenPlugin(project);

      verify(springBootRepository).addSpringBootMavenPlugin(project);
    }

    @Test
    void shouldAddMainApp() {
      Project project = tmpProjectWithBuildGradle();

      springBootDomainService.addMainApp(project);

      verify(springBootRepository).addMainApp(project);
    }

    @Test
    void shouldAddApplicationProperties() {
      Project project = tmpProjectWithBuildGradle();

      springBootDomainService.addApplicationProperties(project);

      verify(springBootRepository).addApplicationProperties(project);
    }

    @Test
    void shouldAddApplicationTestProperties() {
      Project project = tmpProjectWithBuildGradle();

      springBootDomainService.addApplicationTestProperties(project);

      verify(springBootRepository).addApplicationTestProperties(project);
    }
  }
}
