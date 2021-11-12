package tech.jhipster.forge.generator.buildtool.maven.infrastructure.primary.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;
import tech.jhipster.forge.generator.buildtool.generic.domain.ParentAdded;
import tech.jhipster.forge.generator.project.application.MavenApplicationService;

@Component
public class MavenParentAddedListener implements ApplicationListener<PayloadApplicationEvent<ParentAdded>> {

  private final MavenApplicationService mavenApplicationService;

  public MavenParentAddedListener(MavenApplicationService mavenApplicationService) {
    this.mavenApplicationService = mavenApplicationService;
  }

  @Override
  public void onApplicationEvent(PayloadApplicationEvent<ParentAdded> event) {
    ParentAdded parentAdded = event.getPayload();
    if (parentAdded.project().isMavenProject()) {
      mavenApplicationService.addParent(parentAdded.project(), parentAdded.parent());
    }
  }
}
