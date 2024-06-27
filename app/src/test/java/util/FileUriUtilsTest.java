package util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import swati4star.createpdf.util.FileUriUtils;

@RunWith(MockitoJUnitRunner.class)
public class FileUriUtilsTest {

    @Mock
    Uri mockUri;

    FileUriUtils fileUriUtils;

    @Before
    public void setUp() {
        fileUriUtils = FileUriUtils.getInstance();
    }

    @Test
    public void testGetFilePath_NullUri_ReturnsNull() {
        // Setup
        when(mockUri.getPath()).thenReturn(null);

        // Execution
        String filePath = fileUriUtils.getFilePath(mockUri);

        // Assertion
        assertEquals(null, filePath);
    }

    @Test
    public void testGetFilePath_ValidUri_ReturnsPath() {
        // Setup
        String expectedPath = "/path/to/file";
        when(mockUri.getPath()).thenReturn(expectedPath);

        // Execution
        String filePath = fileUriUtils.getFilePath(mockUri);

        // Assertion
        assertEquals(expectedPath, filePath);
        fileUriUtils.printCoverage();
    }

}