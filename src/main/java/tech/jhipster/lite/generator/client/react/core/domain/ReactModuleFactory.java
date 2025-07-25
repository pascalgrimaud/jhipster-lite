package tech.jhipster.lite.generator.client.react.core.domain;

import static tech.jhipster.lite.generator.typescript.common.domain.EslintShortcuts.eslintTypescriptRule;
import static tech.jhipster.lite.generator.typescript.common.domain.TsConfigShortcuts.tsConfigCompilerOption;
import static tech.jhipster.lite.generator.typescript.common.domain.VitestShortcuts.vitestCoverageExclusion;
import static tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.LINE_BREAK;
import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.lineAfterRegex;
import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.packageName;
import static tech.jhipster.lite.module.domain.JHipsterModule.path;
import static tech.jhipster.lite.module.domain.JHipsterModule.preCommitCommands;
import static tech.jhipster.lite.module.domain.JHipsterModule.regex;
import static tech.jhipster.lite.module.domain.JHipsterModule.scriptCommand;
import static tech.jhipster.lite.module.domain.JHipsterModule.scriptKey;
import static tech.jhipster.lite.module.domain.JHipsterModule.stagedFilesFilter;
import static tech.jhipster.lite.module.domain.JHipsterModule.text;
import static tech.jhipster.lite.module.domain.JHipsterModule.to;
import static tech.jhipster.lite.module.domain.nodejs.JHLiteNodePackagesVersionSource.COMMON;
import static tech.jhipster.lite.module.domain.nodejs.JHLiteNodePackagesVersionSource.REACT;
import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.always;

import java.util.function.Consumer;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.nodejs.NodeLazyPackagesInstaller;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class ReactModuleFactory {

  private static final JHipsterSource SOURCE = from("client/react/core");

  private static final JHipsterSource WEBAPP_SOURCE = SOURCE.append("src/main/webapp");
  private static final JHipsterDestination WEBAPP_DESTINATION = to("src/main/webapp");

  private static final JHipsterSource APP_SOURCE = WEBAPP_SOURCE.append("app");
  private static final JHipsterDestination APP_DESTINATION = WEBAPP_DESTINATION.append("app");

  private static final JHipsterSource PIQURE_SOURCE = from("client/common/piqure");

  private static final String PRIMARY_APP = "home/infrastructure/primary";
  private static final String ASSETS = "assets";

  private static final JHipsterSource PRIMARY_APP_SOURCE = APP_SOURCE.append(PRIMARY_APP);
  private static final JHipsterDestination PRIMARY_APP_DESTINATION = APP_DESTINATION.append(PRIMARY_APP);

  private static final String TEST_PRIMARY = "src/test/webapp/unit/home/infrastructure/primary";
  private static final String DEFAULT_TSCONFIG_PATH = "\"@/*\": [\"src/main/webapp/app/*\"]";

  private final NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  public ReactModuleFactory(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    this.nodeLazyPackagesInstaller = nodeLazyPackagesInstaller;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .preCommitActions(stagedFilesFilter("{src/**/,}*.{ts,tsx}"), preCommitCommands("eslint --fix", "prettier --write"))
      .packageJson()
        .removeDevDependency(packageName("@tsconfig/recommended"))
        .addDevDependency(packageName("@testing-library/dom"), REACT)
        .addDevDependency(packageName("@testing-library/react"), REACT)
        .addDevDependency(packageName("@types/node"), COMMON)
        .addDevDependency(packageName("@types/react"), REACT)
        .addDevDependency(packageName("@types/react-dom"), REACT)
        .addDevDependency(packageName("@tsconfig/vite-react"), REACT)
        .addDevDependency(packageName("@vitejs/plugin-react"), REACT)
        .addDevDependency(packageName("eslint-plugin-react"), REACT)
        .addDevDependency(packageName("jsdom"), COMMON)
        .addDevDependency(packageName("vite"), COMMON)
        .addDependency(packageName("react"), REACT)
        .addDependency(packageName("react-dom"), REACT)
        .addDependency(packageName("piqure"), COMMON)
        .addScript(scriptKey("dev"), scriptCommand("npm-run-all --parallel dev:*"))
        .addScript(scriptKey("dev:vite"), scriptCommand("vite"))
        .addScript(scriptKey("build"), scriptCommand("npm-run-all build:*"))
        .addScript(scriptKey("build:tsc"), scriptCommand("tsc -b"))
        .addScript(scriptKey("build:vite"), scriptCommand("vite build --emptyOutDir"))
        .addScript(scriptKey("preview"), scriptCommand("vite preview"))
        .addScript(scriptKey("start"), scriptCommand("vite"))
        .and()
      .postActions()
        .add(context -> nodeLazyPackagesInstaller.runInstallIn(context.projectFolder(), properties.nodePackageManager()))
        .and()
      .files()
        .batch(SOURCE, to("."))
          .addTemplate("vite.config.ts")
          .and()
        .batch(APP_SOURCE, APP_DESTINATION)
          .addTemplate("index.css")
          .addTemplate("index.tsx")
          .addTemplate("vite-env.d.ts")
          .and()
        .batch(PIQURE_SOURCE, APP_DESTINATION)
          .addTemplate("injections.ts")
        .and()
        .add(WEBAPP_SOURCE.template("index.html"), WEBAPP_DESTINATION.append("index.html"))
        .add(SOURCE.append(TEST_PRIMARY).template("HomePage.spec.tsx"), to(TEST_PRIMARY).append("HomePage.spec.tsx"))
        .add(PRIMARY_APP_SOURCE.template("HomePage.tsx"), PRIMARY_APP_DESTINATION.append("HomePage.tsx"))
        .add(PRIMARY_APP_SOURCE.template("HomePage.css"), PRIMARY_APP_DESTINATION.append("HomePage.css"))
        .batch(WEBAPP_SOURCE.append(ASSETS), WEBAPP_DESTINATION.append(ASSETS))
          .addFile("JHipster-Lite-neon-blue.png")
          .addFile("ReactLogo.png")
          .and()
        .and()
      .apply(patchEslintConfig(properties))
      .apply(patchTsConfig(properties))
      .apply(patchVitestConfig())
      .build();
    // @formatter:on
  }

  private Consumer<JHipsterModuleBuilder> patchEslintConfig(JHipsterModuleProperties properties) {
    String reactConfig = """
      \t\tfiles: ['src/main/webapp/**/*.{ts,tsx}', 'src/test/webapp/unit/**/*.{ts,tsx}'],
      \t\textends: [...typescript.configs.recommendedTypeChecked, react],
      \t\tsettings: {
      \t\t\treact: {
      \t\t\t\tversion: 'detect',
      \t\t\t},
      \t\t},\
      """.replace("\t", properties.indentation().spaces());
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("eslint.config.js"))
          .add(lineAfterRegex("from 'typescript-eslint'"), "import react from 'eslint-plugin-react/configs/recommended.js';")
            .add(regex(always(), "\\s+\\.\\.\\.typescript\\.configs\\.recommended.*"), "")
            .add(regex("[ \\t]+files: \\['src/\\*/webapp/\\*\\*\\/\\*\\.ts'],"), reactConfig)
            .add(
              lineAfterRegex("globals: \\{ \\.\\.\\.globals\\.browser },"),
              """
              \t\t\tparserOptions: {
              \t\t\t\tproject: ['./tsconfig.json'],
              \t\t\t},\
              """.replace("\t", properties.indentation().spaces())
            )
            .add(
              regex("[ \\t]+quotes: \\['error', 'single', \\{ avoidEscape: true }],"),
              """
              \t\t\t'@typescript-eslint/no-misused-promises': [
              \t\t\t\t'error',
              \t\t\t\t{
              \t\t\t\t\tchecksVoidReturn: false,
              \t\t\t\t},
              \t\t\t],\
              """.replace("\t", properties.indentation().spaces())
            )
          .and()
        .and()
      .apply(eslintTypescriptRule("'@typescript-eslint/consistent-type-imports': 'error'", properties.indentation()))
      .apply(eslintTypescriptRule("'@typescript-eslint/await-thenable': 'off'", properties.indentation()))
      .apply(eslintTypescriptRule("'@typescript-eslint/no-explicit-any': 'off'", properties.indentation()))
      .apply(eslintTypescriptRule("'react/react-in-jsx-scope': 'off'", properties.indentation()));
    // @formatter:on
  }

  private Consumer<JHipsterModuleBuilder> patchTsConfig(JHipsterModuleProperties properties) {
    String pathsReplacement =
      DEFAULT_TSCONFIG_PATH + "," + LINE_BREAK + properties.indentation().times(3) + "\"@assets/*\": [\"src/main/webapp/assets/*\"]";
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("tsconfig.json"))
          .add(text("@tsconfig/recommended/tsconfig.json"), "@tsconfig/vite-react/tsconfig.json")
          .add(text(DEFAULT_TSCONFIG_PATH), pathsReplacement)
          .and()
      .and()
      .apply(tsConfigCompilerOption("composite", false, properties.indentation()))
      .apply(tsConfigCompilerOption("forceConsistentCasingInFileNames", true, properties.indentation()))
      .apply(tsConfigCompilerOption("allowSyntheticDefaultImports", true, properties.indentation()));
    // @formatter:on
  }

  private Consumer<JHipsterModuleBuilder> patchVitestConfig() {
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("vitest.config.ts"))
          .add(lineAfterRegex("from 'vitest/config';"), "import react from '@vitejs/plugin-react';")
          .add(text("plugins: ["), "plugins: [react(), ")
          .add(text("environment: 'node',"), "environment: 'jsdom',")
          .and()
      .and()
      .apply(vitestCoverageExclusion("src/main/webapp/app/index.tsx"))
      .apply(vitestCoverageExclusion("src/main/webapp/app/injections.ts"));
    // @formatter:on
  }
}
