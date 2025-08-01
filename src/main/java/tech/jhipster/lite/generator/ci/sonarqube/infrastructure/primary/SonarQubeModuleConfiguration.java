package tech.jhipster.lite.generator.ci.sonarqube.infrastructure.primary;

import static tech.jhipster.lite.shared.slug.domain.JHLiteFeatureSlug.CODE_COVERAGE_JAVA;
import static tech.jhipster.lite.shared.slug.domain.JHLiteFeatureSlug.JAVA_BUILD_TOOL;
import static tech.jhipster.lite.shared.slug.domain.JHLiteFeatureSlug.SONARQUBE;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.SONARQUBE_JAVA_BACKEND;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.SONARQUBE_JAVA_BACKEND_AND_FRONTEND;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.SONARQUBE_TYPESCRIPT;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.TYPESCRIPT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.ci.sonarqube.application.SonarQubeApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SonarQubeModuleConfiguration {

  @Bean
  JHipsterModuleResource sonarqubeBackendModule(SonarQubeApplicationService sonarQube) {
    return JHipsterModuleResource.builder()
      .slug(SONARQUBE_JAVA_BACKEND)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("SonarQube", "Add Sonar configuration for Java Backend to inspect code quality")
      .organization(
        JHipsterModuleOrganization.builder().feature(SONARQUBE).addDependency(JAVA_BUILD_TOOL).addDependency(CODE_COVERAGE_JAVA).build()
      )
      .tags("server", "sonar", "sonarqube")
      .factory(sonarQube::buildBackendModule);
  }

  @Bean
  JHipsterModuleResource sonarqubeBackendFrontendModule(SonarQubeApplicationService sonarQube) {
    return JHipsterModuleResource.builder()
      .slug(SONARQUBE_JAVA_BACKEND_AND_FRONTEND)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("SonarQube", "Add Sonar configuration for Java Backend and Frontend to inspect code quality")
      .organization(
        JHipsterModuleOrganization.builder().feature(SONARQUBE).addDependency(JAVA_BUILD_TOOL).addDependency(CODE_COVERAGE_JAVA).build()
      )
      .tags("server", "frontend", "sonar", "sonarqube")
      .factory(sonarQube::buildBackendFrontendModule);
  }

  @Bean
  JHipsterModuleResource sonarqubeTypescriptModule(SonarQubeApplicationService sonarTypescript) {
    return JHipsterModuleResource.builder()
      .slug(SONARQUBE_TYPESCRIPT)
      .withoutProperties()
      .apiDoc("Typescript", "Add Sonar to project")
      .organization(JHipsterModuleOrganization.builder().feature(SONARQUBE).addDependency(TYPESCRIPT).build())
      .tags("typescript")
      .factory(sonarTypescript::buildTypescriptModule);
  }

  private JHipsterModulePropertiesDefinition propertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().addProjectName().addProjectBaseName().addIndentation().build();
  }
}
