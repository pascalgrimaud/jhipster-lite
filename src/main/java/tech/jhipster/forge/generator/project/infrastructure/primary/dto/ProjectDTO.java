package tech.jhipster.forge.generator.project.infrastructure.primary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import tech.jhipster.forge.error.domain.Assert;
import tech.jhipster.forge.generator.project.domain.model.Project;

@ApiModel(value = "ProjectDTO", description = "Project DTO")
public class ProjectDTO {

  @JsonProperty("folder")
  @ApiModelProperty(value = "path", example = "/tmp/jhforge/jhipster", required = true)
  private String folder;

  @JsonProperty("generator-jhipster")
  @ApiModelProperty(value = "generator-jhipster configuration", required = true)
  private Map<String, Object> generatorJhipster;

  public String getFolder() {
    return folder;
  }

  public Map<String, Object> getGeneratorJhipster() {
    return generatorJhipster;
  }

  public static Project toProject(ProjectDTO projectDTO) {
    Assert.notNull("projectDTO", projectDTO);

    return Project.builder().folder(projectDTO.getFolder()).config(projectDTO.getGeneratorJhipster()).build();
  }

  public ProjectDTO folder(String path) {
    this.folder = path;
    return this;
  }

  public ProjectDTO generatorJhipster(Map<String, Object> generatorJhipster) {
    this.generatorJhipster = generatorJhipster;
    return this;
  }
}
