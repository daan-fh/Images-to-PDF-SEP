package util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static swati4star.createpdf.util.FileUtils.getFileName;

import android.app.Activity;
import android.net.Uri;
import swati4star.createpdf.R;

//import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.TimeZone;

import swati4star.createpdf.util.FileInfoUtils;
import swati4star.createpdf.util.FileUriUtils;
import swati4star.createpdf.util.FileUtils;

//import static org.mockito.Mockito.*;

//import org.mockito.Mockito;
//import android.app.Activity;
import android.content.Context;
//import android.content.SharedPreferences;
//import android.net.Uri;

@RunWith(MockitoJUnitRunner.class)
public class FileUtilsTest {

    private static final String FILE_PATH = "/a/b/";
    private static final String FILE_NAME = "c.pdf";
    private FileUtils fileUtils;
    private Activity activity;
    private Context context;
    //private SharedPreferences mockSharedPreferences;

    @Mock
    File file;


    @BeforeEach
    public void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        activity = mock(Activity.class);
        context = mock(Context.class);
        //mockSharedPreferences = mock(SharedPreferences.class);
        when(activity.getApplicationContext()).thenReturn(context);
        fileUtils = new FileUtils(activity);
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
        assertThat(fileUtils.getFileNameWithoutExtension(FILE_PATH + FILE_NAME), is("c"));
        assertThat(fileUtils.getFileNameWithoutExtension(""), is(""));
    }

    @Test
    public void when_CallingGetFileDirectoryPath_Expect_CorrectValueReturned() {
        assertThat(fileUtils.getFileDirectoryPath(FILE_PATH + FILE_NAME), is(FILE_PATH));
        assertThat(fileUtils.getFileDirectoryPath(""), is(""));
    }

    @Test
    public void testGetUriRealPath_NullUri() {
        Uri uri = null;
        String result = fileUtils.getUriRealPath(uri);
        assertNull(result);
    }

    @Test
    public void testGetUriRealPath_WhatsAppUri() {
        Uri uri = Uri.parse("content://com.whatsapp.provider.media/item/12345");
        when(FileUriUtils.getInstance().isWhatsappImage(uri.getAuthority())).thenReturn(true);
        String result = fileUtils.getUriRealPath(uri);
        assertNull(result);
    }

    @Test
    public void testGetUriRealPath_RegularUri() {
        Uri uri = Uri.parse("content://media/external/images/media/12345");
        when(FileUriUtils.getInstance().getUriRealPathAboveKitkat(context, uri)).thenReturn("/path/to/image.jpg");
        String result = fileUtils.getUriRealPath(uri);
        assertEquals("/path/to/image.jpg", result);
    }

    @Test
    public void testGetLastFileName_EmptyList() {
        ArrayList<String> filePaths = new ArrayList<>();
        String result = fileUtils.getLastFileName(filePaths);
        assertEquals("", result);
    }

    @Test
    public void testGetLastFileName_NonEmptyList() {
        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add("/storage/emulated/0/Download/file1.pdf");
        filePaths.add("/storage/emulated/0/Download/file2.pdf");
        when(activity.getString(R.string.pdf_suffix)).thenReturn("_pdf");
        String result = fileUtils.getLastFileName(filePaths);
        assertEquals("file2_pdf", result);
    }

}