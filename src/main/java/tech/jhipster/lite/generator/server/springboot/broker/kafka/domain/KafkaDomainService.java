package tech.jhipster.lite.generator.server.springboot.broker.kafka.domain;

import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

import java.util.TreeMap;

import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.DASHERIZED_BASE_NAME;

public class KafkaDomainService implements KafkaService {

  public static final String SOURCE = "server/springboot/broker/kafka";

  private final BuildToolService buildToolService;

  private final ProjectRepository projectRepository;

  private final SpringBootCommonService springBootCommonService;

  public KafkaDomainService(final BuildToolService buildToolService, final ProjectRepository projectRepository, final SpringBootCommonService springBootCommonService) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
  }

  @Override
  public void init(final Project project) {
    addApacheKafkaClient(project);
    addDockerCompose(project);
    addProperties(project);
    addTestcontainers(project);
  }

  private void addApacheKafkaClient(final Project project) {
    Dependency dependency = Dependency.builder().groupId("org.apache.kafka").artifactId("kafka-clients").build();
    buildToolService.addDependency(project, dependency);
  }

  private void addDockerCompose(final Project project) {
    project.addDefaultConfig(BASE_NAME);
    project.addConfig("zookeeperDockerImage", Zookeeper.getZookeeperDockerImage());
    project.addConfig("kafkaDockerImage", Kafka.getKafkaDockerImage());
    projectRepository.template(project, SOURCE, "kafka.yml", "src/main/docker", "kafka.yml");
  }

  private void addProperties(final Project project) {
    getKafkaCommonProperties().forEach((k, v) -> springBootCommonService.addProperties(project, k, v));
  }

  private TreeMap<String, Object> getKafkaCommonProperties() {
    TreeMap<String, Object> result = new TreeMap<>();

    result.put("kafka.bootstrap-servers", "localhost:9092");
    result.put("kafka.consumer.key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    result.put("kafka.consumer.value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    result.put("kafka.consumer.group.id", DASHERIZED_BASE_NAME);
    result.put("kafka.consumer.auto.offset.reset", "earliest");
    result.put("kafka.producer.key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    result.put("kafka.producer.value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    return result;
  }

  private void addTestcontainers(final Project project) {
    Dependency dependency = Dependency.builder().groupId("org.testcontainers").artifactId("kafka").build();
    buildToolService.addDependency(project, dependency);
  }
}
