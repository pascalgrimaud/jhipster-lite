package tech.jhipster.forge.generator.project.application;

import org.springframework.stereotype.Service;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.service.init.InitService;

@Service
public class InitApplicationService {

  private final InitService initService;

  public InitApplicationService(InitService initService) {
    this.initService = initService;
  }

  public void init(Project project) {
    initService.init(project);
  }
}
