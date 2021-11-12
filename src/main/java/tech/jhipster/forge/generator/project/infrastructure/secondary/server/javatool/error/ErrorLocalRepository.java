package tech.jhipster.forge.generator.project.infrastructure.secondary.server.javatool.error;

import static tech.jhipster.forge.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.generator.project.domain.model.Constants.MAIN_JAVA;
import static tech.jhipster.forge.generator.project.domain.model.Constants.TEST_JAVA;
import static tech.jhipster.forge.generator.project.domain.model.DefaultConfig.PACKAGE_NAME;

import org.springframework.stereotype.Repository;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;
import tech.jhipster.forge.generator.project.domain.service.server.javatool.error.ErrorRepository;

@Repository
public class ErrorLocalRepository implements ErrorRepository {

  public static final String SOURCE = "server/javatool/error";

  private final ProjectRepository projectRepository;

  public ErrorLocalRepository(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void init(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));
    String errorDomainPath = "error/domain";

    projectRepository.template(project, SOURCE, "Assert.java", getPath(MAIN_JAVA, packageNamePath, errorDomainPath));
    projectRepository.template(
      project,
      SOURCE,
      "MissingMandatoryValueException.java",
      getPath(MAIN_JAVA, packageNamePath, errorDomainPath)
    );
    projectRepository.template(project, SOURCE, "UnauthorizedValueException.java", getPath(MAIN_JAVA, packageNamePath, errorDomainPath));

    projectRepository.template(project, SOURCE, "AssertTest.java", getPath(TEST_JAVA, packageNamePath, errorDomainPath));
    projectRepository.template(
      project,
      SOURCE,
      "MissingMandatoryValueExceptionTest.java",
      getPath(TEST_JAVA, packageNamePath, errorDomainPath)
    );
    projectRepository.template(
      project,
      SOURCE,
      "UnauthorizedValueExceptionTest.java",
      getPath(TEST_JAVA, packageNamePath, errorDomainPath)
    );
  }
}
