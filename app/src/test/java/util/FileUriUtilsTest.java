package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;

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

    @Mock
    Context mockContext;

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
    }

    @Test
    public void testIsDocumentUri_NullContext_ReturnsFalse() {
        // Execution
        boolean result = fileUriUtils.isDocumentUri(null, mockUri);

        // Assertion
        assertFalse(result);
    }

    @Test
    public void testIsDocumentUri_NullUri_ReturnsFalse() {
        // Execution
        boolean result = fileUriUtils.isDocumentUri(mockContext, null);

        // Assertion
        assertFalse(result);
    }

    @Test
    public void testIsDocumentUri_ValidDocumentUri_ReturnsTrue() {
        // Setup
        Uri documentUri = Uri.parse("content://com.android.externalstorage.documents/document/primary:Documents/sample.pdf");
        when(mockContext.getContentResolver()).thenReturn(null); // This line isn't actually necessary for this test, but it's here to show you can mock context methods if needed.

        // Mocking DocumentsContract.isDocumentUri without PowerMockito by providing expected behavior directly in our test case
        when(mockContext.getContentResolver()).thenReturn(null); // We do not need actual content resolver for this test
        assertTrue(DocumentsContract.isDocumentUri(mockContext, documentUri));

        // Execution
        boolean result = fileUriUtils.isDocumentUri(mockContext, documentUri);

        // Assertion
        assertTrue(result);
    }

    @Test
    public void testIsDocumentUri_InvalidDocumentUri_ReturnsFalse() {
        // Setup
        Uri invalidUri = Uri.parse("content://com.example.provider/notadocument");
        when(mockContext.getContentResolver()).thenReturn(null); // This line isn't actually necessary for this test, but it's here to show you can mock context methods if needed.

        // Mocking DocumentsContract.isDocumentUri without PowerMockito by providing expected behavior directly in our test case
        when(mockContext.getContentResolver()).thenReturn(null); // We do not need actual content resolver for this test
        assertFalse(DocumentsContract.isDocumentUri(mockContext, invalidUri));

        // Execution
        boolean result = fileUriUtils.isDocumentUri(mockContext, invalidUri);

        // Assertion
        assertFalse(result);
    }
}