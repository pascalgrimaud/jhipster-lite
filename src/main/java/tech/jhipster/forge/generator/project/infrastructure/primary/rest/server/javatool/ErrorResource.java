package tech.jhipster.forge.generator.project.infrastructure.primary.rest.server.javatool;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.forge.generator.project.application.ErrorApplicationService;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.infrastructure.primary.dto.ProjectDTO;

@RestController
@RequestMapping("/api/servers/java/error")
@Api(tags = "Java Tool")
class ErrorResource {

  private final ErrorApplicationService errorApplicationService;

  public ErrorResource(ErrorApplicationService errorApplicationService) {
    this.errorApplicationService = errorApplicationService;
  }

  @ApiOperation("Add Error domain to project")
  @ApiResponses({ @ApiResponse(code = 500, message = "An error occurred while initializing project") })
  @PostMapping
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    errorApplicationService.init(project);
  }
}
