package tech.jhipster.forge.generator.project.domain.service.server.springboot.core;

public class SpringBoot {

  public static final String SPRING_BOOT_VERSION = "2.5.6";
  public static final String NEEDLE_APPLICATION_PROPERTIES = "# jhipster-needle-application-properties";
  public static final String NEEDLE_APPLICATION_TEST_PROPERTIES = "# jhipster-needle-application-test-properties";
  public static final String APPLICATION_PROPERTIES = "application.properties";

  private SpringBoot() {}

  public static String getVersion() {
    return SPRING_BOOT_VERSION;
  }
}
