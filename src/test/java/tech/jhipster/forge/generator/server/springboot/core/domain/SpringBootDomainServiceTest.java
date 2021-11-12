package tech.jhipster.forge.generator.server.springboot.core.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static tech.jhipster.forge.TestUtils.tmpProjectWithPomXml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.generator.buildtool.generic.domain.BuildToolRepository;
import tech.jhipster.forge.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.forge.generator.buildtool.generic.domain.Parent;
import tech.jhipster.forge.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SpringBootDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolRepository buildToolRepository;

  SpringBootDomainService springBootDomainService;

  @BeforeEach
  void setUp() {
    springBootDomainService = new SpringBootDomainService(projectRepository, buildToolRepository);
  }

  @Test
  void shouldInit() throws Exception {
    Project project = tmpProjectWithPomXml();

    springBootDomainService.init(project);

    verify(buildToolRepository).addParent(any(Project.class), any(Parent.class));
    verify(buildToolRepository, times(3)).addDependency(any(Project.class), any(Dependency.class));
    verify(buildToolRepository).addPlugin(any(Project.class), any(Plugin.class));

    // for application.properties
    verify(projectRepository, times(1)).template(any(Project.class), anyString(), anyString(), anyString());
    // for main class + test
    verify(projectRepository, times(3)).template(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddSpringBootParent() throws Exception {
    Project project = tmpProjectWithPomXml();

    springBootDomainService.addSpringBootParent(project);

    verify(buildToolRepository).addParent(any(Project.class), any(Parent.class));
  }

  @Test
  void shouldAddSpringBootDependencies() throws Exception {
    Project project = tmpProjectWithPomXml();

    springBootDomainService.addSpringBootDependencies(project);

    verify(buildToolRepository, times(3)).addDependency(any(Project.class), any(Dependency.class));
  }

  @Test
  void shouldAddSpringBootPlugin() throws Exception {
    Project project = tmpProjectWithPomXml();

    springBootDomainService.addSpringBootMavenPlugin(project);

    verify(buildToolRepository).addPlugin(any(Project.class), any(Plugin.class));
  }
}
