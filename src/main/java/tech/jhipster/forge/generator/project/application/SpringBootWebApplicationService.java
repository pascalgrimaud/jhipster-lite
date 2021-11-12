package tech.jhipster.forge.generator.project.application;

import org.springframework.stereotype.Service;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.server.springboot.web.domain.SpringBootWebService;

@Service
public class SpringBootWebApplicationService {

  private final SpringBootWebService springBootWebService;

  public SpringBootWebApplicationService(SpringBootWebService springBootWebService) {
    this.springBootWebService = springBootWebService;
  }

  public void init(Project project) {
    this.springBootWebService.init(project);
  }

  public void addSpringBootWeb(Project project) {
    springBootWebService.addSpringBootWeb(project);
  }

  public void addSpringBootUndertow(Project project) {
    springBootWebService.addSpringBootUndertow(project);
  }
}
