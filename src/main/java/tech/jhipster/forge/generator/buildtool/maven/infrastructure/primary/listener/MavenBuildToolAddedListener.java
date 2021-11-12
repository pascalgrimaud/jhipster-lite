package tech.jhipster.forge.generator.buildtool.maven.infrastructure.primary.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;
import tech.jhipster.forge.generator.buildtool.generic.domain.BuildToolAdded;
import tech.jhipster.forge.generator.project.application.MavenApplicationService;
import tech.jhipster.forge.generator.project.domain.model.BuildToolType;

@Component
public class MavenBuildToolAddedListener implements ApplicationListener<PayloadApplicationEvent<BuildToolAdded>> {

  private final MavenApplicationService mavenApplicationService;

  public MavenBuildToolAddedListener(MavenApplicationService mavenApplicationService) {
    this.mavenApplicationService = mavenApplicationService;
  }

  @Override
  public void onApplicationEvent(PayloadApplicationEvent<BuildToolAdded> event) {
    BuildToolAdded buildToolAdded = event.getPayload();
    if (buildToolAdded.buildTool() == BuildToolType.MAVEN) {
      mavenApplicationService.init(buildToolAdded.project());
    }
  }
}
