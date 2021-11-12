package tech.jhipster.forge.generator.project.domain.model;

public interface ProjectRepository {
  void create(Project project);

  void add(Project project, String source, String sourceFilename);
  void add(Project project, String source, String sourceFilename, String destination);
  void add(Project project, String source, String sourceFilename, String destination, String destinationFilename);

  void template(Project project, String source, String sourceFilename);
  void template(Project project, String source, String sourceFilename, String destination);
  void template(Project project, String source, String sourceFilename, String destination, String destinationFilename);

  void write(Project project, String text, String destination, String destinationFilename);
}
