package tech.jhipster.forge.generator.project.infrastructure.secondary.init;

import static tech.jhipster.forge.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.generator.project.domain.model.DefaultConfig.*;

import org.springframework.stereotype.Repository;
import tech.jhipster.forge.common.domain.WordUtils;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;
import tech.jhipster.forge.generator.project.domain.service.init.Init;
import tech.jhipster.forge.generator.project.domain.service.init.InitRepository;

@Repository
public class InitLocalRepository implements InitRepository {

  public static final String SOURCE = "init";

  private final ProjectRepository projectRepository;

  public InitLocalRepository(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addPackageJson(Project project) {
    project.addDefaultConfig(PROJECT_NAME);
    project.addDefaultConfig(BASE_NAME);

    project.addConfig("nodeVersion", Init.getNodeVersion());

    String baseName = project.getBaseName().orElse("");
    project.addConfig("dasherizedBaseName", WordUtils.kebabCase(baseName));

    projectRepository.template(project, SOURCE, "package.json");
  }

  @Override
  public void addReadme(Project project) {
    project.addDefaultConfig(PROJECT_NAME);

    projectRepository.template(project, SOURCE, "README.md");
  }

  @Override
  public void addGitConfiguration(Project project) {
    projectRepository.add(project, SOURCE, "gitignore", ".", ".gitignore");
    projectRepository.add(project, SOURCE, "gitattributes", ".", ".gitattributes");
  }

  @Override
  public void addEditorConfiguration(Project project) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

    projectRepository.template(project, SOURCE, ".editorconfig");
    projectRepository.add(project, SOURCE, ".eslintignore");
  }

  @Override
  public void addPrettier(Project project) {
    projectRepository.add(project, SOURCE, ".lintstagedrc.js");
    projectRepository.add(project, SOURCE, ".prettierignore");
    projectRepository.add(project, getPath(SOURCE, ".husky"), "pre-commit", ".husky");

    projectRepository.template(project, SOURCE, ".prettierrc");
  }
}
