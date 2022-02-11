package tech.jhipster.lite.generator.server.springboot.broker.kafka.domain;

import tech.jhipster.lite.generator.project.domain.Project;

public interface KafkaService {
  void init(Project project);

  void addProducer(Project project);
}
