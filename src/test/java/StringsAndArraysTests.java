import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.rasedgwick.algorithms.StringsAndArrays;

import static org.junit.jupiter.api.Assertions.*;

public class StringsAndArraysTests {
    @ParameterizedTest(name = "#{index} - Run isUniqueChars test with args = {0}")
    @NullAndEmptySource
    @ValueSource(strings = {"AaBbCc", "\\nbc"})
    public void isUniqueCharsTest_False(String str){
       boolean result = StringsAndArrays.isUniqueChars(str);
       assertFalse(result);
    }

    @ParameterizedTest(name = "#{index} - Run isUniqueChars test with args = {0}")
    @ValueSource(strings = {"a", "abc", " abc"})
    public void isUniqueCharsTest_True(String str){
        boolean result = StringsAndArrays.isUniqueChars(str);
        assertTrue(result);
    }


    @ParameterizedTest(name = "#{index} - Run isPermutation test with args = {0}")
    @CsvSource({
            "abc, abcc",
            "abc, ab c",
            "abc, ab c ",
            "ab2, abcd",
            "\\nbc, \nbc"
    })
    public void isPermutationTest_Fasle(String s, String t){
        boolean result = StringsAndArrays.isPermutation(s, t);
        assertFalse(result);
    }

    @ParameterizedTest(name = "#{index} - Run isPermutation test with args = {0}, {1}")
    @CsvSource({
            "abc, abc",
            "a bc, ab c",
            "ab2, 2bc",
            "\\nbc, \\ncb"
    })
    public void isPermutationTest_True(String s, String t){
        boolean result = StringsAndArrays.isPermutation(s, t);
        assertTrue(result);
    }

    @ParameterizedTest(name = "#{index} - Run URLify test with args = {0}")
    @CsvSource({
            "mytest.com/ab cd efg, mytest.com/ab%20cd%20efg",
            " mytest.com/abcdefg , %20mytest.com/abcdefg%20",
            "\"\", \"\"",
    })
    public void URLifyTest(String input, String expected){
        String result = StringsAndArrays.URLify(input, input.length());
        assertEquals(expected, result);
    }
}
