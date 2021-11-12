package tech.jhipster.forge.generator.project.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.forge.TestUtils.assertFileExist;
import static tech.jhipster.forge.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.generator.project.domain.model.Constants.TEST_TEMPLATE_RESOURCES;

import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.common.domain.FileUtils;

@UnitTest
class GitUtilsTest {

  @Test
  void shouldInitThenAddThenCommit() throws Exception {
    String dir = FileUtils.tmpDirForTest();

    GitUtils.init(dir);
    File file = File.createTempFile("hello", ".world", new File(dir));

    GitUtils.add(dir);
    GitUtils.commit(dir, "1st commit");

    assertFileExist(getPath(dir, ".git"));
    assertFileExist(getPath(dir, file.getName()));
  }

  @Test
  void shouldInitThenAddAndCommit() throws Exception {
    String dir = FileUtils.tmpDirForTest();

    GitUtils.init(dir);
    File file = File.createTempFile("hello", ".world", new File(dir));

    GitUtils.addAndCommit(dir, "1st commit");

    assertFileExist(getPath(dir, ".git"));
    assertFileExist(getPath(dir, file.getName()));
  }

  @Test
  void shouldApply() throws Exception {
    String dir = FileUtils.tmpDirForTest();
    GitUtils.init(dir);

    GitUtils.apply(dir, getPath(TEST_TEMPLATE_RESOURCES, "utils", "example.patch"));

    assertFileExist(getPath(dir, "example.md"));
  }

  @Test
  void shouldNotApplyWhenNoExistingPatch() throws Exception {
    String dir = FileUtils.tmpDirForTest();
    GitUtils.init(dir);

    assertThatThrownBy(() -> GitUtils.apply(dir, getPath(TEST_TEMPLATE_RESOURCES, "utils", "unknown.patch")))
      .isExactlyInstanceOf(FileNotFoundException.class);
  }
}
