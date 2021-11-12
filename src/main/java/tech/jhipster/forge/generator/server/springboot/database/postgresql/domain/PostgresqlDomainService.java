package tech.jhipster.forge.generator.server.springboot.database.postgresql.domain;

import static tech.jhipster.forge.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.generator.project.domain.model.Constants.MAIN_JAVA;
import static tech.jhipster.forge.generator.project.domain.model.Constants.TEST_JAVA;
import static tech.jhipster.forge.generator.project.domain.model.DefaultConfig.BASE_NAME;
import static tech.jhipster.forge.generator.project.domain.model.DefaultConfig.PACKAGE_NAME;

import java.util.Map;
import java.util.TreeMap;
import tech.jhipster.forge.generator.buildtool.generic.domain.BuildToolRepository;
import tech.jhipster.forge.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;
import tech.jhipster.forge.generator.server.springboot.properties.domain.SpringBootPropertiesService;

public class PostgresqlDomainService implements PostgresqlService {

  public static final String SOURCE = "server/springboot/database/postgresql";

  private final ProjectRepository projectRepository;
  private final BuildToolRepository buildToolRepository;
  private final SpringBootPropertiesService springBootPropertiesService;

  public PostgresqlDomainService(
    ProjectRepository projectRepository,
    BuildToolRepository buildToolRepository,
    SpringBootPropertiesService springBootPropertiesService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolRepository = buildToolRepository;
    this.springBootPropertiesService = springBootPropertiesService;
  }

  @Override
  public void init(Project project) {
    addSpringDataJpa(project);
    addPostgreSQLDriver(project);
    addHikari(project);
    addHibernateCore(project);
    addDockerCompose(project);
    addDialectJava(project);
    addProperties(project);
    addTestcontainers(project);
  }

  @Override
  public void addSpringDataJpa(Project project) {
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-data-jpa").build();

    buildToolRepository.addDependency(project, dependency);
  }

  @Override
  public void addPostgreSQLDriver(Project project) {
    Dependency dependency = Dependency.builder().groupId("org.postgresql").artifactId("postgresql").build();

    buildToolRepository.addDependency(project, dependency);
  }

  @Override
  public void addHikari(Project project) {
    Dependency dependency = Dependency.builder().groupId("com.zaxxer").artifactId("HikariCP").build();

    buildToolRepository.addDependency(project, dependency);
  }

  @Override
  public void addHibernateCore(Project project) {
    Dependency dependency = Dependency.builder().groupId("org.hibernate").artifactId("hibernate-core").build();

    buildToolRepository.addDependency(project, dependency);
  }

  @Override
  public void addDockerCompose(Project project) {
    projectRepository.template(project, SOURCE, "postgresql.yml", "src/main/docker", "postgresql.yml");
  }

  @Override
  public void addDialectJava(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));
    String dialectPath = "technical/secondary/postgresql";

    projectRepository.template(
      project,
      SOURCE,
      "FixedPostgreSQL10Dialect.java",
      getPath(MAIN_JAVA, packageNamePath, dialectPath),
      "FixedPostgreSQL10Dialect.java"
    );
    projectRepository.template(
      project,
      SOURCE,
      "FixedPostgreSQL10DialectTest.java",
      getPath(TEST_JAVA, packageNamePath, dialectPath),
      "FixedPostgreSQL10DialectTest.java"
    );
  }

  @Override
  public void addProperties(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");
    String packageName = project.getPackageName().orElse("com.mycompany.myapp");

    springProperties(baseName, packageName).forEach((k, v) -> springBootPropertiesService.addProperties(project, k, v));
  }

  @Override
  public void addTestcontainers(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");
    String packageName = project.getPackageName().orElse("com.mycompany.myapp");
    Dependency dependency = Dependency
      .builder()
      .groupId("org.testcontainers")
      .artifactId("postgresql")
      .version("\\${testcontainers.version}")
      .scope("test")
      .build();
    buildToolRepository.addProperty(project, "testcontainers", Postgresql.getTestcontainersVersion());
    buildToolRepository.addDependency(project, dependency);

    springPropertiesForTest(baseName).forEach((k, v) -> springBootPropertiesService.addPropertiesTest(project, k, v));
  }

  private Map<String, Object> springProperties(String baseName, String packageName) {
    TreeMap<String, Object> result = new TreeMap<>();
    result.put("spring.datasource.type", "com.zaxxer.hikari.HikariDataSource");
    result.put("spring.datasource.url", "jdbc:postgresql://localhost:5432/" + baseName);
    result.put("spring.datasource.username", baseName);
    result.put("spring.datasource.password", "");
    result.put("spring.datasource.driver-class-name", "org.postgresql.Driver");
    result.put("spring.datasource.hikari.poolName", "Hikari");
    result.put("spring.datasource.hikari.auto-commit", false);

    result.put("spring.data.jpa.repositories.bootstrap-mode", "deferred");
    result.put("spring.jpa.database-platform", packageName + ".technical.secondary.postgresql.FixedPostgreSQL10Dialect");
    result.put("spring.jpa.properties.hibernate.jdbc.time_zone", "UTC");
    result.put("spring.jpa.open-in-view", false);
    result.put("spring.jpa.properties.hibernate.id.new_generator_mappings", "true");
    result.put("spring.jpa.properties.hibernate.connection.provider_disables_autocommit", "true");
    result.put("spring.jpa.properties.hibernate.generate_statistics", false);

    result.put("spring.jpa.properties.hibernate.jdbc.batch_size", "25");
    result.put("spring.jpa.properties.hibernate.order_inserts", "true");
    result.put("spring.jpa.properties.hibernate.order_updates", "true");
    result.put("spring.jpa.properties.hibernate.query.fail_on_pagination_over_collection_fetch", "true");
    result.put("spring.jpa.properties.hibernate.query.in_clause_parameter_padding", "true");
    result.put("spring.jpa.hibernate.ddl-auto", "none");
    result.put("spring.jpa.hibernate.naming.physical-strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
    result.put("spring.jpa.hibernate.naming.implicit-strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
    return result;
  }

  private Map<String, Object> springPropertiesForTest(String baseName) {
    TreeMap<String, Object> result = new TreeMap<>();
    result.put("spring.datasource.driver-class-name", "org.testcontainers.jdbc.ContainerDatabaseDriver");
    result.put("spring.datasource.url", "jdbc:tc:postgresql:13.4:///" + baseName + "?TC_TMPFS=/testtmpfs:rw");
    result.put("spring.datasource.username", baseName);
    result.put("spring.datasource.password", "");
    return result;
  }
}
