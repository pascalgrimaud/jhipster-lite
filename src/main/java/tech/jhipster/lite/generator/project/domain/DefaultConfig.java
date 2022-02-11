package tech.jhipster.lite.generator.project.domain;

import static tech.jhipster.lite.common.domain.WordUtils.DEFAULT_INDENTATION;

import java.util.Map;
import java.util.Optional;

public class DefaultConfig {

  public static final String BASE_NAME = "baseName";
  public static final String DASHERIZED_BASE_NAME = "base-name";
  public static final String PROJECT_NAME = "projectName";
  public static final String PACKAGE_NAME = "packageName";
  public static final String PRETTIER_DEFAULT_INDENT = "prettierDefaultIndent";

  // prettier-ignore
  public static final Map<String, Object> defaultMap = Map.of(
    BASE_NAME, "jhipster",
    PROJECT_NAME, "JHipster Project",
    PACKAGE_NAME, "com.mycompany.myapp",
    PRETTIER_DEFAULT_INDENT, DEFAULT_INDENTATION
  );

  public static final String PACKAGE_PATH = "com/mycompany/myapp";

  private DefaultConfig() {}

  public static Optional<Object> get(String key) {
    return Optional.ofNullable(defaultMap.get(key));
  }
}
