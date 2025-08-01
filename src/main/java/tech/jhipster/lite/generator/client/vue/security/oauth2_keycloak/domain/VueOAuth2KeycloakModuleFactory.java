package tech.jhipster.lite.generator.client.vue.security.oauth2_keycloak.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.documentationTitle;
import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.lineBeforeText;
import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.packageName;
import static tech.jhipster.lite.module.domain.JHipsterModule.path;
import static tech.jhipster.lite.module.domain.JHipsterModule.to;
import static tech.jhipster.lite.module.domain.nodejs.JHLiteNodePackagesVersionSource.COMMON;

import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class VueOAuth2KeycloakModuleFactory {

  private static final JHipsterSource SOURCE = from("client/vue");
  private static final JHipsterSource APP_SOURCE = from("client/vue/security/oauth2_keycloak/webapp/app");
  private static final JHipsterSource DOCUMENTATION_SOURCE = SOURCE.append("documentation");

  private static final JHipsterDestination MAIN_DESTINATION = to("src/main/webapp/app");
  private static final JHipsterDestination TEST_DESTINATION = to("src/test/webapp");

  private static final String MAIN_TS_IMPORT_NEEDLE = "// jhipster-needle-main-ts-import";
  private static final String MAIN_TS_PROVIDER_NEEDLE = "// jhipster-needle-main-ts-provider";

  private static final String KEYCLOAK_IMPORT = """
    import { provideForAuth } from '@/auth/application/AuthProvider';
    import { KeycloakHttp } from '@/auth/infrastructure/secondary/KeycloakHttp';
    import Keycloak from 'keycloak-js';\
    """;
  private static final String KEYCLOAK_SETUP = """
    const keycloakHttp = new KeycloakHttp(
    %snew Keycloak({
    %surl: 'http://localhost:9080',
    %srealm: 'jhipster',
    %sclientId: 'web_app',
    %s}),
    );

    provideForAuth(keycloakHttp);\
    """;

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    Indentation indentation = properties.indentation();

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Vue OAuth2 Keycloak Authentication Components"),
                      DOCUMENTATION_SOURCE.template("vue-oauth2-keycloak-authentication-components.md"))
      .packageJson()
        .addDependency(packageName("keycloak-js"), COMMON)
        .and()
      .files()
        .batch(APP_SOURCE.append("auth"), MAIN_DESTINATION.append("auth"))
          .addTemplate("application/AuthProvider.ts")
          .addTemplate("domain/AuthRepository.ts")
          .addTemplate("domain/AuthenticatedUser.ts")
          .addTemplate("infrastructure/secondary/KeycloakAuthRepository.ts")
          .addTemplate("infrastructure/secondary/KeycloakHttp.ts")
          .and()
        .add(APP_SOURCE.template("test/webapp/unit/auth/application/AuthProvider.spec.ts"), TEST_DESTINATION.append("unit/auth/application/AuthProvider.spec.ts"))
        .batch(APP_SOURCE.append("test/webapp/unit/auth/infrastructure/secondary"), TEST_DESTINATION.append("unit/auth/infrastructure/secondary"))
          .addTemplate("KeycloakAuthRepository.spec.ts")
          .addTemplate("KeycloakHttp.spec.ts")
          .addTemplate("KeycloakHttpStub.ts")
          .addTemplate("KeycloakStub.ts")
          .and()
        .and()
      .mandatoryReplacements()
        .in(path("src/main/webapp/app/main.ts"))
          .add(lineBeforeText(MAIN_TS_IMPORT_NEEDLE),
            KEYCLOAK_IMPORT
          )
          .add(lineBeforeText(MAIN_TS_PROVIDER_NEEDLE),
            KEYCLOAK_SETUP.formatted(indentation.spaces(),
                          indentation.times(2),
                          indentation.times(2),
                          indentation.times(2),
                          indentation.spaces())
          )
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
