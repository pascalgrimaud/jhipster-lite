package tech.jhipster.forge.generator.server.springboot.banner.infrastructure.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.forge.generator.project.domain.model.ProjectRepository;
import tech.jhipster.forge.generator.server.springboot.banner.domain.BannerDomainService;
import tech.jhipster.forge.generator.server.springboot.banner.domain.BannerService;

@Configuration
public class BannerServiceBeanConfiguration {

  private final ProjectRepository projectRepository;

  public BannerServiceBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public BannerService bannerService() {
    return new BannerDomainService(projectRepository);
  }
}
