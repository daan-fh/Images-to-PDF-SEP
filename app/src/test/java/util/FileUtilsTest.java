package util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static swati4star.createpdf.util.FileUtils.getFileName;

import android.app.Activity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.TimeZone;
import swati4star.createpdf.util.FileUtils;
import swati4star.createpdf.util.FileInfoUtils;

@RunWith(MockitoJUnitRunner.class)
public class FileUtilsTest {
    private static final String FILE_PATH = "/a/b/";
    private static final String FILE_NAME = "c.pdf";
    @Mock
    Activity activity;
    FileUtils fileUtils;
    File file;

    @Before
    public void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        fileUtils = new FileUtils(activity);
    }

    @Test
    public void when_StripExtension_WithExtension_Expect_ExtensionStripped() {
        String fileName = "example.pdf";
        assertThat(fileUtils.stripExtension(fileName), is("example"));
    }

    @Test
    public void when_StripExtension_WithoutExtension_Expect_SameFileName() {
        String fileName = "example";
        assertThat(fileUtils.stripExtension(fileName), is("example"));
    }

    @Test
    public void when_StripExtension_NullInput_Expect_NullOutput() {
        assertThat(fileUtils.stripExtension(null), Matchers.nullValue());
    }

    @Test
    public void when_CallingGetFormattedDate_Expect_CorrectDateReturned() {
        when(file.lastModified()).thenReturn(0L);
        assertThat(FileInfoUtils.getFormattedDate(file), is("Thu, Jan 01 at 00:00"));
    }

    @Test
    public void when_CallingGetFormattedSize_Expect_CorrectDateReturned() {
        when(file.length()).thenReturn(5242880L);
        assertThat(FileInfoUtils.getFormattedSize(file), is("5.00 MB"));
    }

    @Test
    public void when_CallingGetFileName_Expect_CorrectValueReturned() {
        assertThat(getFileName(FILE_PATH + FILE_NAME), is(FILE_NAME));
        assertThat(getFileName(""), is(""));
    }

    @Test
    public void when_CallingStaticGetFileName_Expect_CorrectValueReturned() {
        assertThat(getFileName(FILE_PATH + FILE_NAME), is(FILE_NAME));
        assertThat(getFileName(""), is(""));
    }

    @Test
    public void when_CallingGetFileNameWithoutExtension_Expect_CorrectValueReturned() {
        assertThat(FileUtils.getFileNameWithoutExtension(FILE_PATH + FILE_NAME), is("c"));
        assertThat(FileUtils.getFileNameWithoutExtension(""), is(""));
    }

    @Test
    public void when_CallingGetFileDirectoryPath_Expect_CorrectValueReturned() {
        assertThat(FileUtils.getFileDirectoryPath(FILE_PATH + FILE_NAME), is(FILE_PATH));
        assertThat(FileUtils.getFileDirectoryPath(""), is(""));
    }
}
