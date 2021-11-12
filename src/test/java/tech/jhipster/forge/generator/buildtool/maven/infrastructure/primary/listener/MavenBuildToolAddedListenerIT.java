package tech.jhipster.forge.generator.buildtool.maven.infrastructure.primary.listener;

import static tech.jhipster.forge.TestUtils.assertFileExist;
import static tech.jhipster.forge.TestUtils.assertFileNotExist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.common.domain.FileUtils;
import tech.jhipster.forge.generator.buildtool.generic.domain.BuildToolAdded;
import tech.jhipster.forge.generator.project.domain.BuildToolType;
import tech.jhipster.forge.generator.project.domain.Project;

@IntegrationTest
class MavenBuildToolAddedListenerIT {

  @Autowired
  ApplicationEventPublisher publisher;

  @Test
  void shouldInit() {
    Project project = Project.builder().folder(FileUtils.tmpDirForTest()).build();
    BuildToolAdded buildToolAdded = BuildToolAdded.of(project, BuildToolType.MAVEN);

    publisher.publishEvent(buildToolAdded);

    assertFileExist(project, "pom.xml");
    assertFileExist(project, "mvnw");
    assertFileExist(project, "mvnw.cmd");
    assertFileExist(project, ".mvn/wrapper/MavenWrapperDownloader.java");
    assertFileExist(project, ".mvn/wrapper/maven-wrapper.jar");
    assertFileExist(project, ".mvn/wrapper/maven-wrapper.properties");
  }

  @Test
  void shouldNotInit() {
    Project project = Project.builder().folder(FileUtils.tmpDirForTest()).build();
    BuildToolAdded buildToolAdded = BuildToolAdded.of(project, BuildToolType.GRADLE);

    publisher.publishEvent(buildToolAdded);

    assertFileNotExist(project, "pom.xml");
    assertFileNotExist(project, "mvnw");
    assertFileNotExist(project, "mvnw.cmd");
    assertFileNotExist(project, ".mvn/wrapper/MavenWrapperDownloader.java");
    assertFileNotExist(project, ".mvn/wrapper/maven-wrapper.jar");
    assertFileNotExist(project, ".mvn/wrapper/maven-wrapper.properties");
  }
}
