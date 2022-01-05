package tech.jhipster.lite.generator.packagemanager.npm.domain;

import static tech.jhipster.lite.common.domain.WordUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.PACKAGE_JSON;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import tech.jhipster.lite.generator.project.domain.CommandRepository;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class NpmDomainService implements NpmService {

  public static final String VERSION = DQ + "version" + DQ;
  public static final String DEPENDENCIES = DQ + "dependencies" + DQ;
  public static final String DEV_DEPENDENCIES = DQ + "devDependencies" + DQ;
  public static final String SCRIPTS = DQ + "scripts" + DQ;

  private final CommandRepository commandRepository;
  private final ProjectRepository projectRepository;

  public NpmDomainService(CommandRepository commandRepository, ProjectRepository projectRepository) {
    this.commandRepository = commandRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  public void addDependency(Project project, String dependency, String version) {
    addInformationToPackageJson(project, dependency, version, DEPENDENCIES);
  }

  @Override
  public void addDevDependency(Project project, String dependency, String version) {
    addInformationToPackageJson(project, dependency, version, DEV_DEPENDENCIES);
  }

  @Override
  public void addScript(Project project, String name, String cmd) {
    addInformationToPackageJson(project, name, cmd, SCRIPTS);
  }

  private void addInformationToPackageJson(Project project, String dependency, String version, String key) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);
    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(2);

    String needle = key + ": " + OB;
    String newText = needle + System.lineSeparator() + indent(2, indent) + DQ + dependency + DQ + ": " + DQ + version + DQ;

    String versionNeedle = VERSION;
    if (!projectRepository.containsRegexp(project, "", PACKAGE_JSON, needle)) {
      newText =
        newText + System.lineSeparator() + indent(1, indent) + CB + "," + System.lineSeparator() + indent(1, indent) + versionNeedle;
      projectRepository.replaceText(project, "", PACKAGE_JSON, versionNeedle, newText);
    } else if (projectRepository.containsRegexp(project, "", PACKAGE_JSON, needle + CB)) {
      projectRepository.replaceText(project, "", PACKAGE_JSON, needle, newText + System.lineSeparator() + indent(1, indent));
    } else {
      projectRepository.replaceText(project, "", PACKAGE_JSON, needle, newText + ",");
    }
  }

  @Override
  public void install(Project project) {
    this.commandRepository.npmInstall(project);
  }

  @Override
  public void prettify(Project project) {
    this.commandRepository.npmPrettierFormat(project);
  }
}
