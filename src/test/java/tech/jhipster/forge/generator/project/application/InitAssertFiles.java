package tech.jhipster.forge.generator.project.application;

import static tech.jhipster.forge.TestUtils.assertFileExist;

import tech.jhipster.forge.generator.project.domain.model.Project;

public class InitAssertFiles {

  public static void assertFilesPackageJson(Project project) {
    assertFileExist(project, "package.json");
  }

  public static void assertFilesReadme(Project project) {
    assertFileExist(project, "README.md");
  }

  public static void assertFilesConfiguration(Project project) {
    assertFileExist(project, ".gitignore");
    assertFileExist(project, ".gitattributes");
  }

  public static void assertFilesEditorConfiguration(Project project) {
    assertFileExist(project, ".editorconfig");
    assertFileExist(project, ".eslintignore");
  }

  public static void assertFilesPrettier(Project project) {
    assertFileExist(project, ".husky", "pre-commit");
    assertFileExist(project, ".lintstagedrc.js");
    assertFileExist(project, ".prettierignore");
    assertFileExist(project, ".prettierrc");
  }

  public static void assertFilesInit(Project project) {
    assertFilesPackageJson(project);
    assertFilesReadme(project);
    assertFilesConfiguration(project);
    assertFilesEditorConfiguration(project);
    assertFilesPrettier(project);
  }
}
