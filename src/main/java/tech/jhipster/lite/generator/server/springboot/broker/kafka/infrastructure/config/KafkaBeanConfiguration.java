package tech.jhipster.lite.generator.server.springboot.broker.kafka.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.KafkaDomainService;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.KafkaService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@Configuration
public class KafkaBeanConfiguration {

  public final BuildToolService buildToolService;

  public final ProjectRepository projectRepository;

  public final SpringBootCommonService springBootCommonService;

  public KafkaBeanConfiguration(final BuildToolService buildToolService, final ProjectRepository projectRepository, final SpringBootCommonService springBootCommonService) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
  }

  @Bean
  public KafkaService kafkaService() {
    return new KafkaDomainService(buildToolService, projectRepository, springBootCommonService);
  }
}
