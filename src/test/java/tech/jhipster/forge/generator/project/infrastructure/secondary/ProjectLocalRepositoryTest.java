package tech.jhipster.forge.generator.project.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static tech.jhipster.forge.TestUtils.*;
import static tech.jhipster.forge.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.generator.project.domain.model.Constants.MAIN_RESOURCES;

import com.github.mustachejava.MustacheNotFoundException;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.common.domain.FileUtils;
import tech.jhipster.forge.error.domain.GeneratorException;
import tech.jhipster.forge.error.domain.MissingMandatoryValueException;
import tech.jhipster.forge.generator.project.domain.model.Project;

@UnitTest
@ExtendWith(SpringExtension.class)
class ProjectLocalRepositoryTest {

  @InjectMocks
  ProjectLocalRepository repository;

  @Test
  void shouldCreate() {
    Project project = tmpProject();

    repository.create(project);

    assertFileExist(project.getFolder());
  }

  @Test
  void shouldNotCreate() {
    Project project = tmpProject();

    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      fileUtils.when(() -> FileUtils.createFolder(anyString())).thenThrow(new IOException());

      assertThatThrownBy(() -> repository.create(project)).isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Test
  void shouldAdd() {
    Project project = tmpProject();

    repository.add(project, "mustache", "README.txt");

    assertFileExist(project, "README.txt");
  }

  @Test
  void shouldNotAdd() {
    Project project = tmpProject();
    String randomString = UUID.randomUUID().toString();

    assertThatThrownBy(() -> repository.add(project, "common", randomString)).isInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddWithDestination() {
    Project project = tmpProject();

    repository.add(project, "mustache", "README.txt", getPath(MAIN_RESOURCES));

    assertFileExist(project, MAIN_RESOURCES, "README.txt");
  }

  @Test
  void shouldAddWithDestinationAndDestinationFilename() {
    Project project = tmpProject();

    repository.add(project, "mustache", "README.txt", getPath(MAIN_RESOURCES), "FINAL-README.txt");

    assertFileExist(project, MAIN_RESOURCES, "FINAL-README.txt");
  }

  @Test
  void shouldTemplate() {
    Project project = tmpProject();

    repository.template(project, "mustache", "README.md");

    assertFileExist(project, "README.md");
  }

  @Test
  void shouldNotTemplate() {
    Project project = Project.builder().folder(FileUtils.tmpDirForTest()).build();

    try (MockedStatic<MustacheUtils> mustacheUtils = Mockito.mockStatic(MustacheUtils.class)) {
      mustacheUtils.when(() -> MustacheUtils.template(anyString(), any())).thenThrow(new IOException());

      assertThatThrownBy(() -> repository.template(project, "mustache", "README.md")).isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Test
  void shouldNotTemplateWithNonExistingFile() {
    Project project = tmpProject();

    assertThatThrownBy(() -> repository.template(project, "mustache", "README.md.wrong.mustache"))
      .isInstanceOf(MustacheNotFoundException.class);
  }

  @Test
  void shouldTemplateWithExtension() {
    Project project = tmpProject();

    repository.template(project, "mustache", "README.md.mustache");

    assertFileExist(project, "README.md");
  }

  @Test
  void shouldTemplateWithDestination() {
    Project project = tmpProject();

    repository.template(project, "mustache", "README.md.mustache", getPath(MAIN_RESOURCES));

    assertFileExist(project, "src/main/resources/README.md");
  }

  @Test
  void shouldTemplateWithDestinationAndDestinationFilename() {
    Project project = tmpProject();

    repository.template(project, "mustache", "README.md.mustache", getPath(MAIN_RESOURCES), "FINAL-README.md");

    assertFileExist(project, MAIN_RESOURCES, "FINAL-README.md");
  }

  @Test
  void shouldWrite() {
    Project project = tmpProject();

    repository.write(project, "hello world", "hello", "hello.world");

    assertFileExist(project, "hello/hello.world");
    assertFileContent(project, "hello/hello.world", "hello world");
  }

  @Test
  void shouldNotWriteWhenDestinationCantBeCreated() {
    Project project = tmpProject();
    repository.write(project, "hello world", ".", "hello");

    assertThatThrownBy(() -> repository.write(project, "another hello world", "hello", "hello.world"))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotWriteNullText() {
    Project project = tmpProject();

    assertThatThrownBy(() -> repository.write(project, null, null, null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("text");
  }
}
