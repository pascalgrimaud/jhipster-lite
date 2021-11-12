package tech.jhipster.forge.generator.project.domain.service.buildtool.maven;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.forge.generator.buildtool.generic.domain.Parent;
import tech.jhipster.forge.generator.buildtool.generic.domain.Plugin;

@UnitTest
class MavenTest {

  @Test
  void shouldGetParent() {
    // @formatter:off
    String expected =
      "<parent>" + System.lineSeparator() +
      "    <groupId>org.springframework.boot</groupId>" + System.lineSeparator() +
      "    <artifactId>spring-boot-starter-parent</artifactId>" + System.lineSeparator() +
      "    <version>2.5.3</version>" + System.lineSeparator() +
      "    <relativePath/>" + System.lineSeparator() +
      "  </parent>";
    // @formatter:on
    Parent parent = Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("2.5.3").build();

    assertThat(Maven.getParent(parent)).isEqualTo(expected);
  }

  @Test
  void shouldGetParentWith4Indentations() {
    // @formatter:off
    String expected =
      "<parent>" + System.lineSeparator() +
      "        <groupId>org.springframework.boot</groupId>" + System.lineSeparator() +
      "        <artifactId>spring-boot-starter-parent</artifactId>" + System.lineSeparator() +
      "        <version>2.5.3</version>" + System.lineSeparator() +
      "        <relativePath/>" + System.lineSeparator() +
      "    </parent>";
    // @formatter:on
    Parent parent = Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("2.5.3").build();

    assertThat(Maven.getParent(parent, 4)).isEqualTo(expected);
  }

  @Test
  void shouldGetDependencyMinimal() {
    // @formatter:off
    String expected =
      "<dependency>" + System.lineSeparator() +
      "      <groupId>org.springframework.boot</groupId>" + System.lineSeparator() +
      "      <artifactId>spring-boot-starter</artifactId>" + System.lineSeparator() +
      "    </dependency>";
    // @formatter:on

    Dependency dependency = minimalDependencyBuilder().build();

    assertThat(Maven.getDependency(dependency)).isEqualTo(expected);
  }

  @Test
  void shouldGetDependencyFull() {
    // @formatter:off
    String expected =
      "<dependency>" + System.lineSeparator() +
      "      <groupId>org.springframework.boot</groupId>" + System.lineSeparator() +
      "      <artifactId>spring-boot-starter</artifactId>" + System.lineSeparator() +
      "      <version>2.5.3</version>" + System.lineSeparator() +
      "      <scope>test</scope>" + System.lineSeparator() +
      "    </dependency>";
    // @formatter:on

    Dependency dependency = fullDependencyBuilder().build();

    assertThat(Maven.getDependency(dependency)).isEqualTo(expected);
  }

  @Test
  void shouldGetDependencyFullWith4Indentations() {
    // @formatter:off
    String expected =
      "<dependency>" + System.lineSeparator() +
      "            <groupId>org.springframework.boot</groupId>" + System.lineSeparator() +
      "            <artifactId>spring-boot-starter</artifactId>" + System.lineSeparator() +
      "            <version>2.5.3</version>" + System.lineSeparator() +
      "            <scope>test</scope>" + System.lineSeparator() +
      "        </dependency>";
    // @formatter:on

    Dependency dependency = fullDependencyBuilder().build();

    assertThat(Maven.getDependency(dependency, 4)).isEqualTo(expected);
  }

  @Test
  void shouldGetDependencyWithExclusions() {
    // @formatter:off
    String expected =
      "<dependency>" + System.lineSeparator() +
      "      <groupId>org.springframework.boot</groupId>" + System.lineSeparator() +
      "      <artifactId>spring-boot-starter-web</artifactId>" + System.lineSeparator() +
      "      <exclusions>" + System.lineSeparator() +
      "        <exclusion>" + System.lineSeparator() +
      "          <groupId>org.springframework.boot</groupId>" + System.lineSeparator() +
      "          <artifactId>spring-boot-starter-tomcat</artifactId>" + System.lineSeparator() +
      "        </exclusion>" + System.lineSeparator() +
      "      </exclusions>" + System.lineSeparator() +
      "    </dependency>";
    // @formatter:on

    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-web").build();
    Dependency dependencyToExclude = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-tomcat")
      .build();

    assertThat(Maven.getDependency(dependency, 2, List.of(dependencyToExclude))).isEqualTo(expected);
  }

  @Test
  void shouldGetExclusion() {
    // @formatter:off
    String expected =
      "<exclusion>" + System.lineSeparator() +
      "          <groupId>org.springframework.boot</groupId>" + System.lineSeparator() +
      "          <artifactId>spring-boot-starter-tomcat</artifactId>" + System.lineSeparator() +
      "        </exclusion>";
    // @formatter:off

    Dependency dependencyToExclude = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-tomcat")
      .build();

    assertThat(Maven.getExclusion(dependencyToExclude)).isEqualTo(expected);
  }

  @Test
  void shouldGetExclusions() {
    // @formatter:off
    String expected =
      "<exclusions>" + System.lineSeparator() +
      "        <exclusion>" + System.lineSeparator() +
      "          <groupId>org.springframework.boot</groupId>" + System.lineSeparator() +
      "          <artifactId>spring-boot-starter-tomcat</artifactId>" + System.lineSeparator() +
      "        </exclusion>" + System.lineSeparator() +
      "      </exclusions>";
    // @formatter:off

    Dependency dependencyToExclude = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-tomcat")
      .build();

    assertThat(Maven.getExclusions(List.of(dependencyToExclude))).isEqualTo(expected);
  }

  private Dependency.DependencyBuilder minimalDependencyBuilder() {
    return Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter");
  }

  private Dependency.DependencyBuilder fullDependencyBuilder() {
    return minimalDependencyBuilder().version("2.5.3").scope("test");
  }

  @Test
  void shouldGetPlugin() {
    // @formatter:off
    String expected =
      "<plugin>" + System.lineSeparator() +
      "        <groupId>org.springframework.boot</groupId>" + System.lineSeparator() +
      "        <artifactId>spring-boot-maven-plugin</artifactId>" + System.lineSeparator() +
      "      </plugin>";
    // @formatter:on
    Plugin plugin = fullPluginBuilder().build();

    assertThat(Maven.getPlugin(plugin)).isEqualTo(expected);
  }

  @Test
  void shouldGetPluginWith4Indentations() {
    // @formatter:off
    String expected =
      "<plugin>" + System.lineSeparator() +
      "                <groupId>org.springframework.boot</groupId>" + System.lineSeparator() +
      "                <artifactId>spring-boot-maven-plugin</artifactId>" + System.lineSeparator() +
      "            </plugin>";
    // @formatter:on
    Plugin plugin = fullPluginBuilder().build();

    assertThat(Maven.getPlugin(plugin, 4)).isEqualTo(expected);
  }

  @Test
  void shouldGetProperty() {
    String key = "testcontainers";
    String version = "1.16.0";

    assertThat(Maven.getProperty(key, version)).isEqualTo("<testcontainers.version>1.16.0</testcontainers.version>");
  }

  private Plugin.PluginBuilder fullPluginBuilder() {
    return Plugin.builder().groupId("org.springframework.boot").artifactId("spring-boot-maven-plugin");
  }
}
