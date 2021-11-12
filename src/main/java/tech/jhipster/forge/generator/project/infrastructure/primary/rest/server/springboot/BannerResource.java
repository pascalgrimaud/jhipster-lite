package tech.jhipster.forge.generator.project.infrastructure.primary.rest.server.springboot;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.forge.generator.project.application.BannerApplicationService;
import tech.jhipster.forge.generator.project.domain.model.Project;
import tech.jhipster.forge.generator.project.infrastructure.primary.dto.ProjectDTO;

@RestController
@RequestMapping("/api/servers/spring-boot/banner")
@Api(tags = "Spring Boot")
class BannerResource {

  private final BannerApplicationService bannerApplicationService;

  public BannerResource(BannerApplicationService bannerApplicationService) {
    this.bannerApplicationService = bannerApplicationService;
  }

  @ApiOperation("Add banner JHipster v7 for Angular")
  @ApiResponses({ @ApiResponse(code = 500, message = "An error occurred while initializing project") })
  @PostMapping("/jhipster-v7")
  public void addBannerJHipsterV7(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    bannerApplicationService.addBannerJHipsterV7(project);
  }

  @ApiOperation("Add banner JHipster v7 for React")
  @ApiResponses({ @ApiResponse(code = 500, message = "An error occurred while initializing project") })
  @PostMapping("/jhipster-v7-react")
  public void addBannerJHipsterV7React(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    bannerApplicationService.addBannerJHipsterV7React(project);
  }

  @ApiOperation("Add banner JHipster v7 for Vue")
  @ApiResponses({ @ApiResponse(code = 500, message = "An error occurred while initializing project") })
  @PostMapping("/jhipster-v7-vue")
  public void addBannerJHipsterV7Vue(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    bannerApplicationService.addBannerJHipsterV7Vue(project);
  }

  @ApiOperation("Add banner JHipster v2")
  @ApiResponses({ @ApiResponse(code = 500, message = "An error occurred while initializing project") })
  @PostMapping("/jhipster-v2")
  public void addBannerJHipsterV2(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    bannerApplicationService.addBannerJHipsterV2(project);
  }

  @ApiOperation("Add banner JHipster v3")
  @ApiResponses({ @ApiResponse(code = 500, message = "An error occurred while initializing project") })
  @PostMapping("/jhipster-v3")
  public void addBannerJHipsterV3(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    bannerApplicationService.addBannerJHipsterV3(project);
  }
}
