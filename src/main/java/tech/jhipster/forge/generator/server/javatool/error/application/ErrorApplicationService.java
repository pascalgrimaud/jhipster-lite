package tech.jhipster.forge.generator.server.javatool.error.application;

import org.springframework.stereotype.Service;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.server.javatool.error.domain.ErrorService;

@Service
public class ErrorApplicationService {

  private final ErrorService errorService;

  public ErrorApplicationService(ErrorService errorService) {
    this.errorService = errorService;
  }

  public void init(Project project) {
    errorService.init(project);
  }
}
