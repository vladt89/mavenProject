package application;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Set;

public class TaskLibraryTest {

    private TaskLibrary taskLibrary = new TaskLibrary();

    @Test
    public void testDuplicateNumbers() throws Exception {
        //SETUP SUT
        int[] array = {1, 3, 2, 2, 3};

        //EXERCISE
        Set<Integer> duplicateNumbers = taskLibrary.duplicateNumbers(array);

        //VERIFY
        Assert.assertEquals(2, duplicateNumbers.size());
        Assert.assertTrue(duplicateNumbers.contains(2));
        Assert.assertTrue(duplicateNumbers.contains(3));
    }

    @Test
    public void testDuplicateNumbersWithNoDuplicated() throws Exception {
        //SETUP SUT
        int[] array = {1, 3, 2, 20, 10};

        //EXERCISE
        Set<Integer> duplicateNumbers = taskLibrary.duplicateNumbers(array);

        //VERIFY
        Assert.assertEquals(0, duplicateNumbers.size());
    }

    @Test
    public void testReserveWord() throws Exception {
        //EXERCISE
        String result = taskLibrary.reverseWord("vlad");
        //VERIFY
        Assert.assertEquals("dalv", result);
    }
}