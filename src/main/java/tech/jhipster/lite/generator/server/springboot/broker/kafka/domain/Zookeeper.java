package tech.jhipster.lite.generator.server.springboot.broker.kafka.domain;

public class Zookeeper {

  public static final String ZOOKEEPER_DOCKER_VERSION = "7.0.1";

  public static final String ZOOKEEPER_DOCKER_IMAGE = "confluentinc/cp-zookeeper:" + ZOOKEEPER_DOCKER_VERSION;

  private Zookeeper() {
  }

  public static String getZookeeperDockerImage() {
    return ZOOKEEPER_DOCKER_IMAGE;
  }
}
