package util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static swati4star.createpdf.util.FileUtils.getFileName;

import android.app.Activity;
import android.content.Context;
import android.print.PrintManager;
import android.print.PrintDocumentAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.TimeZone;

import swati4star.createpdf.R;
import swati4star.createpdf.database.DatabaseHelper;
import swati4star.createpdf.util.FileUtils;
import swati4star.createpdf.util.FileInfoUtils;

@RunWith(MockitoJUnitRunner.class)
public class FileUtilsTest {

    private static final String FILE_PATH = "/a/b/";
    private static final String FILE_NAME = "c.pdf";
    private static final String PRINT_JOB_NAME = "AppName Document"; // Replace "AppName" with actual app name

    @Mock
    File file;
    @Mock
    Activity mContext;

    @Mock
    PrintManager printManager;

    @Mock
    DatabaseHelper databaseHelper;

    @Before
    public void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Mockito.when(mContext.getString(R.string.app_name)).thenReturn("AppName");
        Mockito.when(mContext.getSystemService(Context.PRINT_SERVICE)).thenReturn(printManager);
        Mockito.when(mContext.getString(R.string.printed)).thenReturn("printed");
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
        assertThat(getFileName(null), nullValue());
        FileUtils.printCoverage();
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

    @Test
    public void when_CallingPrintFile_Expect_CorrectBehaviorWithPrintManager() {
        File tempFile = new File("/mocked/files/dir/tempFile.pdf");
        FileUtils fileUtils = new FileUtils(mContext);
        fileUtils.printFile(tempFile);
        ArgumentCaptor<PrintDocumentAdapter> adapterCaptor = ArgumentCaptor.forClass(PrintDocumentAdapter.class);
        Mockito.verify(printManager).print(Mockito.eq(PRINT_JOB_NAME), adapterCaptor.capture(), Mockito.isNull());
    }

    @Test
    public void when_CallingPrintFile_Expect_CorrectBehaviorWithoutPrintManager() {
        Mockito.when(mContext.getSystemService(Context.PRINT_SERVICE)).thenReturn(null);
        File tempFile = new File("/mocked/files/dir/tempFile.pdf");
        FileUtils fileUtils = new FileUtils(mContext);
        fileUtils.printFile(tempFile);
        Mockito.verify(printManager, Mockito.never()).print(Mockito.anyString(), Mockito.any(PrintDocumentAdapter.class), Mockito.isNull());
        Mockito.verify(databaseHelper, Mockito.never()).insertRecord(Mockito.anyString(), Mockito.anyString());
    }
}