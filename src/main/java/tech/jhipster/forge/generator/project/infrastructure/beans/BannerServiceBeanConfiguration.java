package tech.jhipster.forge.generator.project.infrastructure.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.banner.BannerDomainService;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.banner.BannerRepository;
import tech.jhipster.forge.generator.project.domain.service.server.springboot.banner.BannerService;

@Configuration
public class BannerServiceBeanConfiguration {

  private final BannerRepository bannerRepository;

  public BannerServiceBeanConfiguration(BannerRepository bannerRepository) {
    this.bannerRepository = bannerRepository;
  }

  @Bean
  public BannerService bannerService() {
    return new BannerDomainService(bannerRepository);
  }
}
