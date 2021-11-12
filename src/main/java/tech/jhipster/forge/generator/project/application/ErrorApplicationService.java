package tech.jhipster.forge.generator.project.application;

import org.springframework.stereotype.Service;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.service.server.javatool.error.ErrorRepository;

@Service
public class ErrorApplicationService {

  private final ErrorRepository errorRepository;

  public ErrorApplicationService(ErrorRepository errorRepository) {
    this.errorRepository = errorRepository;
  }

  public void init(Project project) {
    errorRepository.init(project);
  }
}
