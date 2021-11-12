package tech.jhipster.forge.generator.buildtool.maven.application;

import static tech.jhipster.forge.TestUtils.assertFileExist;

import tech.jhipster.forge.generator.project.domain.model.Project;

public class MavenAssertFiles {

  public static void assertFilesPomXml(Project project) {
    assertFileExist(project, "pom.xml");
  }

  public static void assertFilesMavenWrapper(Project project) {
    assertFileExist(project, "mvnw");
    assertFileExist(project, "mvnw.cmd");
    assertFileExist(project, ".mvn/wrapper/MavenWrapperDownloader.java");
    assertFileExist(project, ".mvn/wrapper/maven-wrapper.jar");
    assertFileExist(project, ".mvn/wrapper/maven-wrapper.properties");
  }

  public static void assertFilesMaven(Project project) {
    assertFilesPomXml(project);
    assertFilesMavenWrapper(project);
  }
}
