package tech.jhipster.forge.generator.buildtool.maven.infrastructure.primary.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;
import tech.jhipster.forge.generator.buildtool.generic.domain.DependencyAdded;
import tech.jhipster.forge.generator.project.application.MavenApplicationService;

@Component
public class MavenDependencyAddedListener implements ApplicationListener<PayloadApplicationEvent<DependencyAdded>> {

  private final MavenApplicationService mavenApplicationService;

  public MavenDependencyAddedListener(MavenApplicationService mavenApplicationService) {
    this.mavenApplicationService = mavenApplicationService;
  }

  @Override
  public void onApplicationEvent(PayloadApplicationEvent<DependencyAdded> event) {
    DependencyAdded dependencyAdded = event.getPayload();
    if (dependencyAdded.project().isMavenProject()) {
      mavenApplicationService.addDependency(dependencyAdded.project(), dependencyAdded.dependency(), dependencyAdded.exclusions());
    }
  }
}
