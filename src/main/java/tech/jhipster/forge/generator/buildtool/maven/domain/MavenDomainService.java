package tech.jhipster.forge.generator.buildtool.maven.domain;

import static tech.jhipster.forge.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.common.domain.FileUtils.read;
import static tech.jhipster.forge.common.domain.WordUtils.indent;
import static tech.jhipster.forge.generator.buildtool.maven.domain.Maven.*;
import static tech.jhipster.forge.generator.project.domain.model.DefaultConfig.*;

import java.io.IOException;
import java.util.List;
import tech.jhipster.forge.common.domain.FileUtils;
import tech.jhipster.forge.common.domain.WordUtils;
import tech.jhipster.forge.error.domain.GeneratorException;
import tech.jhipster.forge.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.forge.generator.buildtool.generic.domain.Parent;
import tech.jhipster.forge.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;

public class MavenDomainService implements MavenService {

  public static final String SOURCE = "buildtool/maven";
  public static final String POM_XML = "pom.xml";

  private final ProjectRepository projectRepository;

  public MavenDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addParent(Project project, Parent parent) {
    try {
      project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);
      int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(2);
      String locationPomXml = getPath(project.getFolder(), POM_XML);
      String currentPomXml = read(locationPomXml);
      String updatedPomXml = FileUtils.replace(currentPomXml, NEEDLE_PARENT, Maven.getParent(parent, indent));

      projectRepository.write(project, updatedPomXml, ".", "pom.xml");
    } catch (IOException e) {
      throw new GeneratorException("Error when adding parent");
    }
  }

  @Override
  public void addDependency(Project project, Dependency dependency) {
    addDependency(project, dependency, List.of());
  }

  @Override
  public void addDependency(Project project, Dependency dependency, List<Dependency> exclusions) {
    try {
      project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);
      int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(2);
      String locationPomXml = getPath(project.getFolder(), POM_XML);
      String currentPomXml = read(locationPomXml);
      String needle = dependency.getScope().orElse("").equals("test") ? NEEDLE_DEPENDENCY_TEST : NEEDLE_DEPENDENCY;
      String dependencyWithNeedle =
        Maven.getDependency(dependency, indent, exclusions) + System.lineSeparator() + indent(2, indent) + needle;
      String updatedPomXml = FileUtils.replace(currentPomXml, needle, dependencyWithNeedle);

      projectRepository.write(project, updatedPomXml, ".", "pom.xml");
    } catch (IOException e) {
      throw new GeneratorException("Error when adding dependency");
    }
  }

  @Override
  public void addPlugin(Project project, Plugin plugin) {
    try {
      project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);
      int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(2);
      String locationPomXml = getPath(project.getFolder(), POM_XML);
      String currentPomXml = read(locationPomXml);
      String pluginWithNeedle = Maven.getPlugin(plugin, indent) + System.lineSeparator() + indent(3, indent) + NEEDLE_PLUGIN;
      String updatedPomXml = FileUtils.replace(currentPomXml, NEEDLE_PLUGIN, pluginWithNeedle);

      projectRepository.write(project, updatedPomXml, ".", "pom.xml");
    } catch (IOException e) {
      throw new GeneratorException("Error when adding dependency");
    }
  }

  @Override
  public void addProperty(Project project, String key, String version) {
    try {
      project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);
      int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(2);
      String locationPomXml = getPath(project.getFolder(), POM_XML);
      String currentPomXml = read(locationPomXml);
      String propertyWithNeedle = Maven.getProperty(key, version) + System.lineSeparator() + indent(2, indent) + NEEDLE_PROPERTIES;
      String updatedPomXml = FileUtils.replace(currentPomXml, NEEDLE_PROPERTIES, propertyWithNeedle);

      projectRepository.write(project, updatedPomXml, ".", "pom.xml");
    } catch (IOException e) {
      throw new GeneratorException("Error when adding dependency");
    }
  }

  @Override
  public void init(Project project) {
    addPomXml(project);
    addMavenWrapper(project);
  }

  @Override
  public void addPomXml(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(PROJECT_NAME);
    project.addDefaultConfig(BASE_NAME);

    String baseName = project.getBaseName().orElse("");
    project.addConfig("dasherizedBaseName", WordUtils.kebabCase(baseName));

    projectRepository.template(project, SOURCE, "pom.xml");
  }

  @Override
  public void addMavenWrapper(Project project) {
    projectRepository.add(project, SOURCE, "mvnw");
    projectRepository.add(project, SOURCE, "mvnw.cmd");

    String sourceWrapper = getPath(SOURCE, ".mvn", "wrapper");
    String destinationWrapper = getPath(".mvn", "wrapper");

    projectRepository.add(project, sourceWrapper, "MavenWrapperDownloader.java", destinationWrapper);
    projectRepository.add(project, sourceWrapper, "maven-wrapper.jar", destinationWrapper);
    projectRepository.add(project, sourceWrapper, "maven-wrapper.properties", destinationWrapper);
  }
}
