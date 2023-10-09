import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.junit.platform.commons.util.StringUtils;
import org.rasedgwick.algorithms.StringsAndArrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        assertEquals("%20123%20", result);
    }

    @ParameterizedTest(name = "#{index} - Run oneAway test with args = {0}, {1}, {2}")
    @CsvSource({
            "pale, pale, true",
            "pale, ple, true",
            "pales, pale, true",
            "pale, bale, true",
            "pale, bae, false"
    })
    public void oneAwayTest(String s, String t, String expectedResult) {
        assertEquals(Boolean.parseBoolean(expectedResult), StringsAndArrays.oneAway(s, t));
    }

    @ParameterizedTest(name = "#{index} - Run simpleStringCompression test with args = {0}, {1}")
    @CsvSource({
            "a, a",
            "abc, abc",
            "aabc, aabc",
            "aabbcc, aabbcc",
            "abbcccdddd, a1b2c3d4",
            "ddddcccbba, d4c3b2a1",
            "aabcccdfffffx, a2b1c3d1f5x1"
    })
    public void simpleStringCompressionTest(String input, String expectedOutput) {
        String result = StringsAndArrays.SimpleStringCompression(input);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void RotateArrayTest() {
        List<int[][]> matrices =TestUtils.getMatrix();
        int[][] result = StringsAndArrays.RotateMatrixNinetyDegreesAlt(matrices.get(0));
        int[][] expected = matrices.get(1);
        assertTrue(TestUtils.areMatricesEqual(result, expected));
    }

    @Test
    public void ZeroArrayTest() {
        List<int[][]> matrices = TestUtils.getZeroArray();
        int[][] result = StringsAndArrays.zeroMatrixRow(matrices.get(0));
        int[][] expected = matrices.get(1);
        assertTrue(TestUtils.areMatricesEqual(result, expected));

    }

    @Test
    public void ZeroArrayRowAndColTest() {
        List<int[][]> matrices = TestUtils.getZeroArrayRowAndCol();
        int[][] result = StringsAndArrays.zeroRowAndCol(matrices.get(0));
        int[][] expected = matrices.get(1);
        assertTrue(TestUtils.areMatricesEqual(result, expected));

    }


    @ParameterizedTest(name = "#{index} - Run simpleStringCompression test with args = {0}, {1}, {2}")
    @CsvSource({
        "waterbottle, erbottlewat, true",
        "taco cat, co catta, true",
        "happybirthday, birthayhappy, false"
    })
    public void stringRotationTest(String s1, String s2, String expectedOutput) {
        boolean result = StringsAndArrays.isStringRotation(s1, s2);
        assertEquals(Boolean.parseBoolean(expectedOutput), result);
    }

    @Test
    public void sumHourGlassTest() {
        List<List<Integer>> matrix = TestUtils.getHourGlassMatrix();
        int result = StringsAndArrays.sumHourGlass(matrix);
        assertEquals(28, result);
    }

    @Test
    public void sumHourGlassTest3() {
        List<List<Integer>> matrix = TestUtils.getHourGlassMatrix3();
        int result = StringsAndArrays.sumHourGlass(matrix);
        assertEquals(-6, result);
    }

    @Test
    public void minimumBribesTests() {
        Integer[] z = new Integer[]{1, 2, 5, 3, 7, 8, 6, 4};
        StringsAndArrays.minimumBribes1(Arrays.stream(z).toList());

        Integer[] a = new Integer[]{1, 2, 3, 5, 4};
        String aResult = StringsAndArrays.minimumBribes(Arrays.stream(a).toList());
        assertEquals("1", aResult);

        Integer[] b = new Integer[]{1, 2, 5, 3, 4};
        String bResult = StringsAndArrays.minimumBribes(Arrays.stream(b).toList());
        assertEquals("2", bResult);

        Integer[] c = new Integer[]{1, 5, 2, 3, 4};
        String cResult = StringsAndArrays.minimumBribes(Arrays.stream(c).toList());
        assertEquals("Too chaotic", cResult);

        Integer[] d = new Integer[]{1, 2, 3, 4};
        String dResult = StringsAndArrays.minimumBribes(Arrays.stream(d).toList());
        assertEquals("0", dResult);

        String eResult = StringsAndArrays.minimumBribes(new ArrayList<>());
        assertEquals("0", eResult);
    }

    @ParameterizedTest(name = "#{index} - Run compressString test with args = {0}, {1}")
    @CsvSource({
            "aab, b",
            "aabb, Empty String",
            "aaabccddd, b",
            "a, a",
            "aaabccdddefghijklmnopqurtuv, bfghijklmnopqurtuv",
            "aaabccddddddddddddddddddddddddd, b"
    })
    public void compressStringTest(String input, String output) {
        String result = StringsAndArrays.compressString(input);
        assertEquals(output, result);
    }

    @ParameterizedTest(name = "#{index} - Run remove duplicates test with args = {0}, {1}")
    @CsvSource({
            "baab, Empty String",
            "aab, b",
            "aabb, Empty String",
            "aaabccddd, abd",
            "a, a",
            "aaabccdddefghijklmnopqurtuv, abdefghijklmnopqurtuv",
            "aaabccddddddddddddddddddddddddd, abd",
            "abccb, a"
    })
    public void removeDuplicatesTest(String input, String output) {
        String result = StringsAndArrays.deleteCharacterPairs(input);
        assertEquals(output, result);
    }

    @ParameterizedTest(name = "#{index} - Run PW Str test with args = {0}, {1}")
    @CsvSource({
        "Ab1, 3",
        "123456, 3",
        "aB1def, 1",
        "@AAA, 2",
        "#cP!F9Z, 0"
    })
    public void removePwStrTest(String input, String output) {
        int result = StringsAndArrays.passwordStr(input, input.length());
        assertEquals(Integer.valueOf(output), result);
    }

    @ParameterizedTest(name = "#{index} - Run alt pair test with args = {0}, {1}")
    @CsvSource({
            "asdcbsdcagfsdbgdfanfghbsfdab, 8",
    })
    public void altPairTest(String input, String output) {
        int result = StringsAndArrays.longestAlternatingPairs(input);
        assertEquals(Integer.valueOf(output), result);
    }

    @ParameterizedTest(name = "#{index} - Run cipher test with args = {0}, {1}, {2}")
    @CsvSource({
            "middle-Outz, 2, okffng-Qwvb",
    })
    public void cipherTest(String input, String k, String output) {
        String result = StringsAndArrays.caesarCipherV2(input, Integer.parseInt(k));
        assertEquals(output, result);
    }

    @ParameterizedTest(name = "#{index} - Run highestValuePalindromeTest test with args = {0}, {1}, {2}")
    @CsvSource({
            "932239, 2, 992299",
            "1231, 3, 9339",
            "12321, 1, 12921",
            "3943, 1, 3993",
            "092282, 3, 992299",
            "0011, 1, -1",
            "1, 1, 9",
            "0000000, 1, 0009000"
    })
    public void highestValuePalindromeTest(String input, String maxChanges, String output) {
        String result = StringsAndArrays.highestValuePalindromeV2(input, input.length(), Integer.parseInt(maxChanges));
        assertEquals(output, result);
    }

    @Test
    public void weightedUniformStringsTests() {
        String s = "abccddde";
        List<Integer> q = new ArrayList<>() {{
            add(1);
            add(3);
            add(12);
            add(5);
            add(9);
            add(10);
        }};
        List<String> expected = new ArrayList<>(){{
            add("Yes");
            add("Yes");
            add("Yes");
            add("Yes");
            add("No");
            add("No");
        }};

        List<String> result = StringsAndArrays.weightedUniformStrings(s, q);
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(result.get(i), expected.get(i));
        }

    }
    @Test
    public void weightedUniformStringsTests2() {
        String s = "aaabbbbcccddd";
        List<Integer> q = new ArrayList<>() {{
            add(9);
            add(7);
            add(8);
            add(12);
            add(5);
        }};
        List<String> expected = new ArrayList<>(){{
            add("Yes"); // 9
            add("No"); // 7
            add("Yes"); // 8
            add("Yes"); // 12
            add("No"); // 5
        }};

        List<String> result = StringsAndArrays.weightedUniformStrings(s, q);
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(result.get(i), expected.get(i));
        }
    }

    @Test
    public void maxPalTest() {
        double[] expected = {(double) 2, (double) 1};
        int[] leftBounds = {1, 4};
        int[] rightBounds = {2, 4};
        List<Double> result = StringsAndArrays.runMaxPalindromes(leftBounds, rightBounds, "week");
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result.get(i));
        }
    }

    @ParameterizedTest(name = "#{index} - Run getMaxPalindromeLength test with args = {0}, {1}")
    @CsvSource({
        "1234567890, 1",
        "12345678901, 3",
        "1, 1",
        "aa, 2",
        "12345aaaabb, 7",
        "aaabbcebbaaa, 11",
        "week, 3",
        "abab, 4"
    })
    public void getMaxPalindromeLengthTest(String str, String expected) {
        char[] chars = str.toCharArray();
        Map<Character, Integer> countMap = StringsAndArrays.getCharacterCount(chars);
        int result = StringsAndArrays.getMaxPalindromeLength(chars.length, countMap.values());
        assertEquals(Integer.parseInt(expected), result);
    }
}
