package tech.jhipster.forge.generator.server.springboot.core.infrastructure.primary.rest;

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
import tech.jhipster.forge.generator.server.springboot.core.application.SpringBootApplicationService;

@RestController
@RequestMapping("/api/servers/spring-boot")
@Api(tags = "Spring Boot")
class SpringBootResource {

  private final SpringBootApplicationService springBootApplicationService;

  public SpringBootResource(SpringBootApplicationService springBootApplicationService) {
    this.springBootApplicationService = springBootApplicationService;
  }

  @ApiOperation("Init Spring Boot project with dependencies, App, and properties")
  @ApiResponses({ @ApiResponse(code = 500, message = "An error occurred while initializing project") })
  @PostMapping
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springBootApplicationService.init(project);
  }
}
