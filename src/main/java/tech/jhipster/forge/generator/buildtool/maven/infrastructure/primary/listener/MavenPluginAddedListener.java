package tech.jhipster.forge.generator.buildtool.maven.infrastructure.primary.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;
import tech.jhipster.forge.generator.buildtool.generic.domain.PluginAdded;
import tech.jhipster.forge.generator.project.application.MavenApplicationService;

@Component
public class MavenPluginAddedListener implements ApplicationListener<PayloadApplicationEvent<PluginAdded>> {

  private final MavenApplicationService mavenApplicationService;

  public MavenPluginAddedListener(MavenApplicationService mavenApplicationService) {
    this.mavenApplicationService = mavenApplicationService;
  }

  @Override
  public void onApplicationEvent(PayloadApplicationEvent<PluginAdded> event) {
    PluginAdded pluginAdded = event.getPayload();
    if (pluginAdded.project().isMavenProject()) {
      mavenApplicationService.addPlugin(pluginAdded.project(), pluginAdded.plugin());
    }
  }
}
