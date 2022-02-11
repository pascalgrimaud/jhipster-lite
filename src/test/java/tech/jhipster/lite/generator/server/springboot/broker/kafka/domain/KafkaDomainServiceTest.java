package tech.jhipster.lite.generator.server.springboot.broker.kafka.domain;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;

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

    kafkaDomainService.init(project);

    verify(buildToolService, times(2)).addDependency(any(Project.class), any(Dependency.class));
    verify(projectRepository).template(any(Project.class), anyString(), anyString(), anyString(), anyString());
    verify(springBootCommonService, times(7)).addProperties(any(Project.class), anyString(), any());
  }
}
