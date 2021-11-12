package tech.jhipster.forge.generator.project.domain.service.server.springboot.database.postgresql;

public class Postgresql {

  public static final String TESTCONTAINERS_VERSION = "1.16.0";

  private Postgresql() {}

  public static String getTestcontainersVersion() {
    return TESTCONTAINERS_VERSION;
  }
}
