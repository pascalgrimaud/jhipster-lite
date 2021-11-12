package tech.jhipster.forge.generator.project.domain.model;

import java.util.Map;
import java.util.Optional;

public class DefaultConfig {

  public static String BASE_NAME = "baseName";
  public static String PROJECT_NAME = "projectName";
  public static String PACKAGE_NAME = "packageName";
  public static String PRETTIER_DEFAULT_INDENT = "prettierDefaultIndent";

  // prettier-ignore
  public static Map<String, Object> defaultMap = Map.of(
    BASE_NAME, "jhipster",
    PROJECT_NAME, "JHipster Project",
    PACKAGE_NAME, "com.mycompany.myapp",
    PRETTIER_DEFAULT_INDENT, 2
  );

  private DefaultConfig() {}

  public static Optional<Object> get(String key) {
    return Optional.ofNullable(defaultMap.get(key));
  }
}
