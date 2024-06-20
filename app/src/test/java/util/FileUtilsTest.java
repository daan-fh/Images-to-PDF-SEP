package util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static swati4star.createpdf.util.FileUtils.getFileName;

import android.app.Activity;
import android.content.Context;
import android.print.PrintManager;
import android.print.PrintDocumentAdapter;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.TimeZone;

import swati4star.createpdf.util.FileInfoUtils;
import swati4star.createpdf.util.FileUtils;

import swati4star.createpdf.R;
import swati4star.createpdf.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.content.res.Resources;

@RunWith(MockitoJUnitRunner.class)
public class FileUtilsTest {

    private static final String FILE_PATH = "/a/b/";
    private static final String FILE_NAME = "c.pdf";
    private static final String PRINT_JOB_NAME = "AppName Document"; // Replace "AppName" with actual app name

    @Mock
    Activity activity;
    FileUtils fileUtils;
    File file;
    @Mock
    Activity mContext;

    @Mock
    PrintManager printManager;

    @Mock
    DatabaseHelper databaseHelper;

    @Mock
    SharedPreferences mSharedPreferences;

    @Mock
    Resources mockResources;

    @Before
    public void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        fileUtils = new FileUtils(activity);
        Mockito.when(mContext.getString(R.string.app_name)).thenReturn("AppName");
        Mockito.when(mContext.getSystemService(Context.PRINT_SERVICE)).thenReturn(printManager);
        Mockito.when(mContext.getString(R.string.printed)).thenReturn("printed");

        when(mContext.getString(R.string.pdf_ext)).thenReturn(".pdf");
        when(mContext.getResources()).thenReturn(mockResources);
        when(mockResources.getString(R.string.pdf_ext)).thenReturn(".pdf");

        lenient().when(mContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mSharedPreferences);
        lenient().when(mSharedPreferences.getString(anyString(), anyString())).thenReturn("/mock/path/pdf/");
    }

    private FileUtils getFileUtilsWithMockedIsFileExist(Activity context, boolean fileExists) {
        FileUtils fileUtils = spy(new FileUtils(context));
        doReturn(fileExists).when(fileUtils).isFileExist(anyString());
        return fileUtils;
    }

    private File createMockFile(String fileName, File parentFile, File[] listFiles) {
        File mockFile = mock(File.class);
        when(mockFile.getName()).thenReturn(fileName);
        when(mockFile.getParentFile()).thenReturn(parentFile);
        if (parentFile != null) {
            when(parentFile.listFiles()).thenReturn(listFiles);
        }
        return mockFile;
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

    @Test
    public void when_FlagIsTrue_Expect_WhileLoopEntered() {
        String finalOutputFile = "/a/b/c.pdf";
        List<File> mFile = new ArrayList<>();

        when(mContext.getString(R.string.pdf_ext)).thenReturn(".pdf");
        FileUtils fileUtils = new FileUtils(mContext);

        fileUtils.checkRepeat(finalOutputFile, mFile);

        assertThat(FileUtils.branchCoverage[0], is(true));
    }

    @Test
    public void when_FileExistsInList_Expect_WhileLoopConditionTrue() {

        String finalOutputFile = "/a/b/c.pdf";
        List<File> mFile = new ArrayList<>();
        mFile.add(new File("/a/b/c1.pdf"));

        when(mContext.getString(R.string.pdf_ext)).thenReturn(".pdf");

        FileUtils fileUtils = new FileUtils(mContext);

        fileUtils.checkRepeat(finalOutputFile, mFile);

        assertThat(FileUtils.branchCoverage[1], is(true));
    }

    @Test
    public void when_FileDoesNotExistInList_Expect_WhileLoopConditionFalse() {
        String finalOutputFile = "/a/b/c.pdf";
        List<File> mFile = new ArrayList<>();

        when(mContext.getString(R.string.pdf_ext)).thenReturn(".pdf");

        FileUtils fileUtils = new FileUtils(mContext);

        fileUtils.checkRepeat(finalOutputFile, mFile);

        assertThat(FileUtils.branchCoverage[2], is(true));
    }

    @Test
    public void when_FileExistsOnceThenNotExists_Expect_WhileLoopEnteredTwice() {

        String finalOutputFile = "/a/b/c.pdf";
        List<File> mFile = new ArrayList<>();
        mFile.add(new File("/a/b/c1.pdf"));

        when(mContext.getString(R.string.pdf_ext)).thenReturn(".pdf");

        FileUtils fileUtils = new FileUtils(mContext);

        fileUtils.checkRepeat(finalOutputFile, mFile);

        assertThat(FileUtils.branchCoverage[1], is(true)); // File exists in the first iteration
        assertThat(FileUtils.branchCoverage[3], is(true)); // Re-enter the loop

    }

    @Test
    public void when_FileNeverExists_Expect_WhileLoopConditionFalse() {

        String finalOutputFile = "/a/b/c.pdf";
        List<File> mFile = new ArrayList<>();

        when(mContext.getString(R.string.pdf_ext)).thenReturn(".pdf");

        FileUtils fileUtils = new FileUtils(mContext);

        fileUtils.checkRepeat(finalOutputFile, mFile);

        assertThat(FileUtils.branchCoverage[2], is(true)); // File never exists
        assertThat(FileUtils.branchCoverage[4], is(true)); // Exit the loop

    }

    @Test
    public void when_FileDoesNotExist_Expect_Branch1True() {
        String fileName = "non_existent_file.pdf";
        FileUtils fileUtils = getFileUtilsWithMockedIsFileExist(mContext, false);

        fileUtils.getUniqueFileName(fileName);

        assertThat(FileUtils.branchCoverage[0], is(true)); // File does not exist
    }

    @Test
    public void when_FileExistsAndParentIsNull_Expect_Branch2And5True() {
        String fileName = "existent_file.pdf";
        FileUtils fileUtils = getFileUtilsWithMockedIsFileExist(mContext, true);

        File mockFile = createMockFile(fileName, null, null);

        doReturn(mockFile).when(fileUtils).createNewFileInstance(fileName);

        fileUtils.getUniqueFileName(fileName);

        assertThat(FileUtils.branchCoverage[1], is(true)); // File exists
        assertThat(FileUtils.branchCoverage[5], is(true)); // Parent is null
    }

    @Test
    public void when_FileExistsAndParentExistsButNoListFiles_Expect_Branch2And4True() {
        String fileName = "existent_file.pdf";
        FileUtils fileUtils = getFileUtilsWithMockedIsFileExist(mContext, true);

        File mockParentFile = mock(File.class);
        File mockFile = createMockFile(fileName, mockParentFile, null);

        doReturn(mockFile).when(fileUtils).createNewFileInstance(fileName);

        fileUtils.getUniqueFileName(fileName);

        assertThat(FileUtils.branchCoverage[1], is(true)); // File exists
        assertThat(FileUtils.branchCoverage[2], is(true)); // Parent exists
        assertThat(FileUtils.branchCoverage[4], is(true)); // listFiles is null
    }

    @Test
    public void when_FileExistsAndParentExistsAndListFilesNotNull_Expect_Branch3True() {
        String fileName = "existent_file.pdf";
        FileUtils fileUtils = getFileUtilsWithMockedIsFileExist(mContext, true);

        File mockParentFile = mock(File.class);
        File[] listFiles = new File[] { new File("/a/b/existent_file1.pdf") };
        File mockFile = createMockFile(fileName, mockParentFile, listFiles);

        doReturn(mockFile).when(fileUtils).createNewFileInstance(fileName);

        fileUtils.getUniqueFileName(fileName);

        assertThat(FileUtils.branchCoverage[1], is(true)); // File exists
        assertThat(FileUtils.branchCoverage[2], is(true)); // Parent exists
        assertThat(FileUtils.branchCoverage[3], is(true)); // listFiles not null
        FileUtils.printCoverage();
    }
}
