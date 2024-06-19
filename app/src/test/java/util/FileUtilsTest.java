package util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import android.app.Activity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.TimeZone;
import swati4star.createpdf.util.FileUtils;

@RunWith(MockitoJUnitRunner.class)
public class FileUtilsTest {
    @Mock
    Activity activity;
    FileUtils fileUtils;

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
}
