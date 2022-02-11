package tech.jhipster.lite.generator.server.springboot.broker.kafka.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.KafkaService;

@Service
public class KafkaApplicationService {

  private final KafkaService kafkaService;

  public KafkaApplicationService(final KafkaService kafkaService) {
    this.kafkaService = kafkaService;
  }

  public void init(final Project project) {
    kafkaService.init(project);
  }
}
