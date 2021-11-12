package tech.jhipster.forge.generator.server.springboot.dbmigration.liquibase.infrastructure.primary.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.forge.generator.server.springboot.dbmigration.liquibase.application.LiquibaseApplicationService;

@RestController
@RequestMapping("/api/servers/spring-boot/dbmigration/liquibase")
@Api(tags = "Liquibase")
class LiquibaseResource {

  private final LiquibaseApplicationService liquibaseApplicationService;

  public LiquibaseResource(LiquibaseApplicationService liquibaseApplicationService) {
    this.liquibaseApplicationService = liquibaseApplicationService;
  }

  @ApiOperation("Add Liquibase")
  @ApiResponses({ @ApiResponse(code = 500, message = "An error occurred while initializing project") })
  @PostMapping
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    liquibaseApplicationService.init(project);
  }
}
