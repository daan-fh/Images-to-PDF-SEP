package util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import swati4star.createpdf.util.WatermarkUtils;

@RunWith(MockitoJUnitRunner.class)
public class WatermarkUtilsTest {

    WatermarkUtils watermarkUtils;

    @Before
    public void setUp() {
        watermarkUtils = new WatermarkUtils(null);
    }

    @Test
    public void getStyleValueFromNameTest() {
        int branchZero = watermarkUtils.getStyleValueFromName("BOLD"); // int value 1
        assertEquals(branchZero, 1);
        int branchOne = watermarkUtils.getStyleValueFromName("ITALIC"); // int value 2
        assertEquals(branchOne, 2);
        int branchTwo = watermarkUtils.getStyleValueFromName("UNDERLINE"); // int value 4
        assertEquals(branchTwo, 4);
        int branchThree = watermarkUtils.getStyleValueFromName("STRIKETHRU"); // int value 8
        assertEquals(branchThree, 8);
        int branchFour = watermarkUtils.getStyleValueFromName("BOLDITALIC"); // int value 3
        assertEquals(branchFour, 3);
        int branchFive = watermarkUtils.getStyleValueFromName("NORMAL"); // int value 0
        assertEquals(branchFive, 0);

        watermarkUtils.printCoverage();
    }
}
