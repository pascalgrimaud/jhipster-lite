package tech.jhipster.forge.generator.server.springboot.web.infrastructure.primary.rest;

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
import tech.jhipster.forge.generator.server.springboot.web.application.SpringBootWebApplicationService;

@RestController
@RequestMapping("/api/servers/spring-boot/web")
@Api(tags = "Spring Boot Web")
class SpringBootWebResource {

  private final SpringBootWebApplicationService springBootWebApplicationService;

  public SpringBootWebResource(SpringBootWebApplicationService springBootWebApplicationService) {
    this.springBootWebApplicationService = springBootWebApplicationService;
  }

  @ApiOperation("Add Spring Boot Web with Tomcat")
  @ApiResponses({ @ApiResponse(code = 500, message = "An error occurred while initializing project") })
  @PostMapping("/tomcat")
  public void addSpringBootWeb(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springBootWebApplicationService.addSpringBootWeb(project);
  }

  @ApiOperation("Add Spring Boot Web with Undertow")
  @ApiResponses({ @ApiResponse(code = 500, message = "An error occurred while initializing project") })
  @PostMapping("/undertow")
  public void addSpringBootUndertow(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springBootWebApplicationService.addSpringBootUndertow(project);
  }
}
