package tech.jhipster.forge.generator.project.infrastructure.primary.rest.server.springboot;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.forge.generator.project.application.PostgresqlApplicationService;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.infrastructure.primary.dto.ProjectDTO;

@RestController
@RequestMapping("/api/servers/spring-boot/databases/postgresql")
@Api(tags = "Spring Boot")
class PostgresqlResource {

  private final PostgresqlApplicationService postgresqlApplicationService;

  public PostgresqlResource(PostgresqlApplicationService postgresqlApplicationService) {
    this.postgresqlApplicationService = postgresqlApplicationService;
  }

  @ApiOperation("Add PostgreSQL drivers and dependencies, with testcontainers")
  @ApiResponses({ @ApiResponse(code = 500, message = "An error occurred while initializing project") })
  @PostMapping
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    postgresqlApplicationService.init(project);
  }
}
