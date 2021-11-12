package tech.jhipster.forge.generator.server.springboot.web.domain;

import tech.jhipster.forge.generator.project.domain.model.Project;

public interface SpringBootWebService {
  void init(Project project);

  void addSpringBootWeb(Project project);
  void addSpringBootUndertow(Project project);
}
