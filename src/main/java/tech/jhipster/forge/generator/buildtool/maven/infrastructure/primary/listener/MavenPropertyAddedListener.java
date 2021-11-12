package tech.jhipster.forge.generator.buildtool.maven.infrastructure.primary.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;
import tech.jhipster.forge.generator.buildtool.generic.domain.PropertyAdded;
import tech.jhipster.forge.generator.project.application.MavenApplicationService;

@Component
public class MavenPropertyAddedListener implements ApplicationListener<PayloadApplicationEvent<PropertyAdded>> {

  private final MavenApplicationService mavenApplicationService;

  public MavenPropertyAddedListener(MavenApplicationService mavenApplicationService) {
    this.mavenApplicationService = mavenApplicationService;
  }

  @Override
  public void onApplicationEvent(PayloadApplicationEvent<PropertyAdded> event) {
    PropertyAdded propertyAdded = event.getPayload();
    if (propertyAdded.project().isMavenProject()) {
      mavenApplicationService.addProperty(propertyAdded.project(), propertyAdded.key(), propertyAdded.version());
    }
  }
}
