package util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static swati4star.createpdf.util.FileUtils.getFileName;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.TimeZone;

import swati4star.createpdf.util.FileInfoUtils;
import swati4star.createpdf.util.FileUtils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;

import swati4star.createpdf.R;


@RunWith(MockitoJUnitRunner.class)
public class FileUtilsTest {

    private static final String FILE_PATH = "/a/b/";
    private static final String FILE_NAME = "c.pdf";

    @Mock
    File file;

    @Mock
    Activity mContext;

    @Mock
    SharedPreferences mSharedPreferences;

    @Mock
    Resources mockResources;

    @Before
    public void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

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

    @Test
    public void when_FlagIsTrue_Expect_WhileLoopEntered() {
        String finalOutputFile = "/a/b/c.pdf";
        List<File> mFile = new ArrayList<>();

        when(mContext.getString(R.string.pdf_ext)).thenReturn(".pdf");
        FileUtils fileUtils = new FileUtils(mContext);

        fileUtils.checkRepeat(finalOutputFile, mFile);

        assertThat(FileUtils.branchCoverage[0], is(true));
        FileUtils.printCoverage();
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
        FileUtils.printCoverage();
    }

    @Test
    public void when_FileDoesNotExistInList_Expect_WhileLoopConditionFalse() {
        String finalOutputFile = "/a/b/c.pdf";
        List<File> mFile = new ArrayList<>();

        when(mContext.getString(R.string.pdf_ext)).thenReturn(".pdf");

        FileUtils fileUtils = new FileUtils(mContext);

        fileUtils.checkRepeat(finalOutputFile, mFile);

        assertThat(FileUtils.branchCoverage[2], is(true));
        FileUtils.printCoverage();
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

        FileUtils.printCoverage();
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

        FileUtils.printCoverage();
    }

    @Test
    public void when_FileDoesNotExist_Expect_Branch1True() {
        String fileName = "non_existent_file.pdf";
        FileUtils fileUtils = getFileUtilsWithMockedIsFileExist(mContext, false);

        fileUtils.getUniqueFileName(fileName);

        assertThat(FileUtils.branchCoverage[0], is(true)); // File does not exist
        FileUtils.printCoverage();
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
        FileUtils.printCoverage();
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
        FileUtils.printCoverage();
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