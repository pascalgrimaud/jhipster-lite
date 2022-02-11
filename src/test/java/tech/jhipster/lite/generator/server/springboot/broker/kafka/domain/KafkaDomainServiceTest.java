package tech.jhipster.lite.generator.server.springboot.broker.kafka.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
public class KafkaDomainServiceTest {

  @Mock
  BuildToolService buildToolService;

  @Mock
  ProjectRepository projectRepository;

  @Mock
  SpringBootCommonService springBootCommonService;

  @InjectMocks
  KafkaDomainService kafkaDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(project, "testcontainers")).thenReturn(Optional.of("0.0.0"));

    kafkaDomainService.init(project);

    verify(buildToolService, times(2)).addDependency(any(Project.class), any(Dependency.class));
    verify(projectRepository).template(any(Project.class), anyString(), anyString(), anyString(), anyString());
    verify(springBootCommonService, times(7)).addProperties(any(Project.class), anyString(), any());
  }

  @Test
  void shouldAddProducer() {
    Project project = tmpProjectWithPomXml();

    kafkaDomainService.addProducer(project);

    verify(projectRepository, times(2)).template(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }
}
