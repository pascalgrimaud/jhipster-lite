package tech.jhipster.forge.generator.project.domain.service.server.javatool.error;

import static tech.jhipster.forge.generator.project.domain.model.LanguageType.JAVA;

import tech.jhipster.forge.error.domain.GeneratorException;
import tech.jhipster.forge.generator.project.domain.model.Project;

public class ErrorDomainService implements ErrorService {

  private final ErrorRepository errorRepository;

  public ErrorDomainService(ErrorRepository errorRepository) {
    this.errorRepository = errorRepository;
  }

  @Override
  public void init(Project project) {
    if (JAVA == project.getLanguage().orElse(null)) {
      errorRepository.init(project);
    } else {
      throw new GeneratorException("No language JAVA");
    }
  }
}
