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
    @ValueSource(strings = {"aa", "abcdd", "abcd a"})
    public void isUniqueCharsTest_False(String str) {
        boolean result = StringsAndArrays.isUniqueChars(str);
        assertFalse(result);
    }

    @ParameterizedTest(name = "#{index} - Run isUniqueChars test with args = {0}")
    @ValueSource(strings = {"a", "abc", " abc", "AaBbCc", "\\nbc"})
    public void isUniqueCharsTest_True(String str) {
        boolean result = StringsAndArrays.isUniqueChars(str);
        assertTrue(result);
    }


    @ParameterizedTest(name = "#{index} - Run isPermutation test with args = {0}, {1}")
    @CsvSource({
            "aabc, abcd",
            "abcd, abcc",
            "abc, ab c",
            "abc, ab c ",
            "ab2, abcd",
            "\\nbc, \nbc"
    })
    public void isPermutationTest_Fasle(String s, String t) {
        boolean result = StringsAndArrays.isPermutation(s, t);
        assertFalse(result);
    }

    @ParameterizedTest(name = "#{index} - Run isPermutation test with args = {0}, {1}")
    @CsvSource({
            "abc, abc",
            "a bc, ab c",
            "ab2, 2ba",
            "\\nbc, \\ncb"
    })
    public void isPermutationTest_True(String s, String t) {
        boolean result = StringsAndArrays.isPermutation(s, t);
        assertTrue(result);
    }

    @ParameterizedTest(name = "#{index} - Run URLify test with args = {0}")
    @CsvSource({
            "mytest.com/ab cd efg, mytest.com/ab%20cd%20efg",
            "\"\", \"\"",
    })
    public void URLifyTest(String input, String expected) {
        String result = StringsAndArrays.URLify(input, input.length());
        assertEquals(expected, result);
    }

    @Test
    public void URLifyTest() {
        String url = " 123 ";
        String result = StringsAndArrays.URLify(url, url.length());
        assertEquals( "%20123%20", result);
    }

}
