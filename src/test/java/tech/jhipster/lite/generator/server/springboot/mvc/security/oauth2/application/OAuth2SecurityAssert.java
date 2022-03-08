package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.*;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2SecurityDomainService.SECURITY_OAUTH2_PATH;

import java.util.List;
import tech.jhipster.lite.generator.project.domain.DefaultConfig;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security;

public class OAuth2SecurityAssert {

  private OAuth2SecurityAssert() {}

  public static void assertSecurityDependencies(Project project) {
    assertFileContent(project, POM_XML, securityDependency());
    assertFileContent(project, POM_XML, oauth2ClientDependency());
    assertFileContent(project, POM_XML, oauth2ResourceServerDependency());

    assertFileContent(project, POM_XML, securityTestDependency());
  }

  public static void assertDockerKeycloak(Project project) {
    assertFileExist(project, "src/main/docker/keycloak.yml");
    assertFileExist(project, "src/main/docker/keycloak-realm-config/jhipster-realm.json");
    assertFileExist(project, "src/main/docker/keycloak-realm-config/jhipster-users-0.json");

    assertFileContent(project, "src/main/docker/keycloak.yml", getDockerKeycloakImage());
  }

  public static void assertJavaFiles(Project project) {
    String oauth2Path = getPath(project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH), SECURITY_OAUTH2_PATH);

    // main java files
    OAuth2Security
      .oauth2SecurityFiles()
      .forEach((javaFile, destination) -> assertFileExist(project, getPath(MAIN_JAVA, oauth2Path, destination, javaFile)));
    OAuth2Security
      .oauth2TestSecurityFiles()
      .forEach((javaFile, destination) -> assertFileExist(project, getPath(TEST_JAVA, oauth2Path, destination, javaFile)));
  }

  public static void assertProperties(Project project) {
    properties().forEach((k, v) -> assertFileContent(project, getPath(MAIN_RESOURCES, "config/application.properties"), k + "=" + v));
    propertiesForTests()
      .forEach((k, v) -> assertFileContent(project, getPath(TEST_RESOURCES, "config/application.properties"), k + "=" + v));
  }

  public static void assertExceptionTranslatorWithSecurity(Project project) {
    String path = getPath(project.getPackageNamePath().orElse("com/mycompany/myapp"), INFRA_PRIMARY, "exception");

    assertFileContent(
      project,
      getPath(MAIN_JAVA, path, "ExceptionTranslator.java"),
      "import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;"
    );
    assertFileContent(
      project,
      getPath(MAIN_JAVA, path, "ExceptionTranslator.java"),
      "public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {"
    );
  }

  public static void assertIntegrationTestWithSecurity(Project project) {
    String path = getPath(project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH));
    String integrationTestFile = getPath(TEST_JAVA, path, "IntegrationTest.java");

    assertFileContent(project, integrationTestFile, "import org.springframework.boot.test.context.SpringBootTest;");

    assertFileContent(project, integrationTestFile, "import org.springframework.security.test.context.support.WithMockUser;");
    assertFileContent(project, integrationTestFile, "@WithMockUser");

    String packageName = project.getPackageName().orElse("com.mycompany.myapp");
    assertFileContent(
      project,
      integrationTestFile,
      "import " + packageName + ".security.oauth2.infrastructure.config.TestSecurityConfiguration;"
    );
    assertFileContent(project, integrationTestFile, "TestSecurityConfiguration.class");
  }

  public static List<String> securityDependency() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-security</artifactId>",
      "</dependency>"
    );
  }

  public static List<String> securityTestDependency() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.security</groupId>",
      "<artifactId>spring-security-test</artifactId>",
      "<scope>test</scope>",
      "</dependency>"
    );
  }

  public static List<String> oauth2ClientDependency() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-oauth2-client</artifactId>",
      "</dependency>"
    );
  }

  public static List<String> oauth2ResourceServerDependency() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>",
      "</dependency>"
    );
  }
}
