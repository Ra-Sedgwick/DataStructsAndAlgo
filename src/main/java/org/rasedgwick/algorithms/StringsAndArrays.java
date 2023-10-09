package org.rasedgwick.algorithms;

import org.rasedgwick.Utils.ArrayHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

public class StringsAndArrays {
    static Logger logger = LoggerFactory.getLogger(StringsAndArrays.class);

    // Determines if all characters in a string are unique.
    public static Boolean isUniqueChars(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        //  If Extended ASCII
        if (str.length() > 256) {
            return false;
        }

        HashSet<Character> charSet = new HashSet<>();

        char[] charArr = str.toCharArray();

        for (char c : charArr) {
            if (charSet.contains(c)) {
                return false;
            }
            charSet.add(c);
        }

        return true;
    }

    // Given two strings determine if one is a permutation of the other
    public static boolean isPermutation(String s, String t) {
        if (s == null ||
                t == null ||
                s.isEmpty() ||
                t.isEmpty() ||
                s.length() != t.length()
        ) {
            return false;
        }

        HashMap<Character, Integer> charCount = new HashMap<>();
        CharacterIterator sCI = new StringCharacterIterator(s);
        CharacterIterator tCI = new StringCharacterIterator(t);

        while (sCI.current() != CharacterIterator.DONE) {
            Integer count = charCount.get(sCI.current());
            if (count == null) {
                count = 0;
            }

            charCount.put(sCI.current(), ++count);

            sCI.next();
        }

        // If the lengths are equal, and not all chars are the same, at least one element
        // will be less than zero and one will be greater than.
        while (tCI.current() != CharacterIterator.DONE) {
            Integer count = charCount.get(tCI.current());
            if (count == null) {
                count = 0;
            }

            charCount.put(tCI.current(), --count);

            if (charCount.get(tCI.current()) < 0) {
                return false;
            }
            tCI.next();
        }

        return true;
    }

    // Give a string replace all white space with '%20`
    public static String URLify(String s, int trueLength) {
        // String.replace would be a trivial solution
        final String encodedWhiteSpace = "%20";
        final int offset = encodedWhiteSpace.length() - 1;
        int length = s.length();
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < length; i++) {
            char c = sb.charAt(i);
            if (Character.isWhitespace(c)) {
                sb.replace(i, i + 1, encodedWhiteSpace);
                i += offset;
                length += offset;
            }
        }


        return sb.toString();
    }

    // Palindrome Permutation (P.P.)
    // Given a string check if its a permutation of a palindrome.
    public boolean palindromePermutation(String str) {
        if (str == null || str.isBlank()) {
            return false;
        }

        // If the length is even, individual char counts must be even 'aabbcc' -> 'abccba'
        // If the length is odd, individual char counts must be even, excluding a character that can have a count of 1, placed in the middle.  'aab' -> 'aba'
        HashMap<Character, Integer> charMap = new HashMap<>();
        CharacterIterator ci = new StringCharacterIterator(str);
        while (ci.current() != CharacterIterator.DONE) {
            if (charMap.containsKey(ci.current())) {
                charMap.put(ci.current(), charMap.get(ci.current()) + 1);
            } else {
                charMap.put(ci.current(), 0);
            }
            ci.next();
        }

        // Logic can be simplified by making sure no more than one character has an odd count.
        // I could also just increment the odd counter as I went and not have to iterate of the counts list
        int oddCount = 0;
        Collection<Integer> charCounts = charMap.values(); // I should have just used a array indexed with charters numeric value instead.
        for (Integer i : charCounts) {
            if (!isEven(i)) {
                oddCount++;
                if (oddCount > 1) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isEven(int num) {
        return num % 2 == 0;
    }

    /**
     * One away
     * There are three types of edits that can be preformed on a string, insert a char, remove a char, replace
     * a char. Given two strings, check if they are one or zero edits away
     */
    public static boolean oneAway(String s1, String s2) {

//        // Check Repalce
//        if (s1.length() == s2.length()) {
//            return checkOneReplace(s1, s1);
//        // Check Add
//        } else if (s1.length() + 1 == s2.length()) {
//            return checkOneInsert(s1, s2);
//        // Check Delete
//        } else if (s1.length() - 1 == s2.length()) {
//            return checkOneInsert(s2, s1);
//        }


        return oneEditAway(s1, s2);
    }

    private static boolean checkOneReplace(String s1, String s2) {
        boolean foundDiff = false;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (foundDiff) {
                    return false;
                }
                foundDiff = true;
            }
        }
        return true;
    }

    // A single delete or add is equivalent.
    private static boolean checkOneInsert(String shortString, String longString) {
        // The two strings are of different length; keep and index for each on
        // to prevent from going out of bounds.
        int shortIndex = 0;
        int longIndex = 0;

        // Iterate over both indexes
        // bae
        // pale
        while (shortIndex < shortString.length() && longIndex < longString.length()) {

            // If charters are not equal at the same position
            char c1 = shortString.charAt(shortIndex);
            char c2 = longString.charAt(longIndex);
            if (c1 != c2) {
                // and the index is out of sync return false
                if (shortIndex != longIndex) {
                    return false;
                }
                // Move past this index
            } else {
                shortIndex++;
            }
            longIndex++;
        }
        return true;
    }

    private static boolean oneEditAway(String s1, String s2) {
        // Length Check, if diff in length is greater than one return false;
        if (Math.abs(s1.length() - s2.length()) > 1) {
            return false;
        }

        // Identify shorter or longer String.
        String longString = s1.length() >= s2.length() ? s1 : s2;
        String shortString = s2.length() <= s1.length() ? s2 : s1;

        int longIndex = 0;
        int shortIndex = 0;
        boolean diffAlreadyFound = false;

        while (longIndex < longString.length() && shortIndex < shortString.length()) {
            if (shortString.charAt(shortIndex) != longString.charAt(longIndex)) {
                if (shortIndex != longIndex ||
                        diffAlreadyFound) {
                    return false;
                } else {
                    diffAlreadyFound = true;
                    if (s1.length() == s2.length()) {
                        longIndex++;
                    }
                    shortIndex++;
                }
            } else {
                shortIndex++;
                longIndex++;
            }
        }
        return true;

    }


    /**
     * Simply String Compression
     * Use the counts of repeated chars to replace chars for example
     * aabcccccaaa -> a2b1c5a3
     * <p>
     * If the compressed version is longer or equal just return the string
     * "a, a",
     * "abc, abc",
     * "aabc, aabc",
     * "aabbcc, aabbcc",
     * "abbcccdddd, a2b2c3d4",
     * "ddddcccbba, d4c3b2a1",
     * "aabcccdfffffx, a2b1c3d1f5x1"
     */
    public static String SimpleStringCompression(String str) {
        if (str == null || str.isBlank()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int index = 0; // Travers array
        int sameCharCursor = 0; // Track same chars

        while (index < str.length()) {
            boolean isSameCharacter = false;
            do {
                if (sameCharCursor < str.length() && // ensure we do not go out of bounds
                        str.charAt(index) == str.charAt(sameCharCursor)) {
                    isSameCharacter = true;
                    sameCharCursor++;
                } else {
                    isSameCharacter = false;
                }
            } while (isSameCharacter);

            sb.append(str.charAt(index));
            int charCount = sameCharCursor - index;
            sb.append(charCount);

            index = sameCharCursor;
        }
        if (sb.length() >= str.length()) {
            return str;
        } else {
            return sb.toString();
        }

    }

    /**
     * Rotate A Matrix
     * Rotate a nxn matrix 90 degrees
     * <p>
     * ab  -> ca
     * cd  -> db
     */
    public static int[][] RotateMatrixNinetyDegrees(int[][] matrix) {

        /**
         * for i = 0
         * temp = top[i]
         * top[i] = left[i]
         * left[i] = bottom[i]
         * bottom[i] = right[i]
         * right[i] = temp.
         *
         *         int[][] inputMatrix = {
         *                 {0, 1, 2},
         *                 {3, 4, 5},
         *                 {6, 7, 8}
         *         };
         *
         *         int[][] outputMatrix = {
         *                 {6, 7, 0},
         *                 {7, 4, 1},
         *                 {8, 5, 3}
         *         };
         */

        ArrayHelper.print2D(matrix);
        int n = matrix.length;
        for (int layer = 0; layer < n / 2; layer++) {

            // first and last index compensated for layer.
            int firstLayerIndex = layer;
            int lastLayerIndex = matrix.length - 1 - layer;

            // Loop over layer with index i;
            for (int i = firstLayerIndex; i < lastLayerIndex; i++) {
                int lastOffset = i - firstLayerIndex;
                logger.info("layer {}\n, first {}\n, last {}\n, lastOffset {}\n", layer, firstLayerIndex, lastLayerIndex, lastOffset);
                int top = matrix[firstLayerIndex][i]; // save top

                // set left - > top
                matrix[firstLayerIndex][i] = matrix[lastLayerIndex - lastOffset][firstLayerIndex];

                // bottom -> left
                matrix[lastLayerIndex - lastOffset][firstLayerIndex] = matrix[lastLayerIndex][lastLayerIndex - lastOffset];

                //right - > bottom
                matrix[lastLayerIndex][lastLayerIndex - lastOffset] = matrix[i][lastLayerIndex];

                //top -> right
                matrix[i][lastLayerIndex] = top;
            }

        }

        ArrayHelper.print2D(matrix);
        return matrix;
    }

    public static int[][] RotateMatrixNinetyDegreesAlt(int[][] matrix) {
        // Starting from the outermost layer [0] moving inwards
        // preform the following swaps
        // Top -> save
        // top -> left
        // left -> bottom
        // bottom -> right
        // right -> save
        ArrayHelper.print2D(matrix);
        int n = matrix.length;
        for (int layer = 0; layer < n / 2; layer++) {
            // Create indexes adjusted for layer
            int firstLayerIndex = layer;
            int lastLayerIndex = n - 1 - layer;

            // Iterate over each edge layer edge
            for (int edgeIndex = firstLayerIndex; edgeIndex < lastLayerIndex; edgeIndex++) {
                // Create index to travers edges backwards
                int edgeOffset = edgeIndex - firstLayerIndex;

                // Save top
                int top = matrix[firstLayerIndex][edgeIndex];

                // left -> top
                matrix[firstLayerIndex][edgeIndex] = matrix[lastLayerIndex - edgeOffset][firstLayerIndex];

                // bottom -> left
                matrix[lastLayerIndex - edgeOffset][firstLayerIndex] = matrix[lastLayerIndex][lastLayerIndex - edgeOffset];

                // bottom -> right
                matrix[lastLayerIndex][lastLayerIndex - edgeOffset] = matrix[edgeIndex][lastLayerIndex];

                // right -> top
                matrix[edgeIndex][lastLayerIndex] = top;


            }
        }
        ArrayHelper.print2D(matrix);
        return matrix;
    }

    public static int[][] zeroMatrixRow(int[][] matrix) {
        ArrayHelper.print2D(matrix);
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] == 0) {
                    zeroRow(matrix, row);
                    col = matrix[row].length - 1;
                }
            }
        }
        ArrayHelper.print2D(matrix);
        return matrix;
    }

    private static void zeroRow(int[][] matrix, int rowIndex) {
        // Could also use array.fill
        for (int col = 0; col < matrix[rowIndex].length; col++) {
            matrix[rowIndex][col] = 0;
        }
    }

    public static int[][] zeroRowAndCol(int[][] matrix) {
        ArrayHelper.print2D(matrix);

        // Get rows and columns that have zeros
        Set<Integer> rowsWithZero = new HashSet<>();
        Set<Integer> colsWithZero = new HashSet<>();
        for (int row = 0; row < matrix.length; row++) {
            if (!rowsWithZero.contains(row)) {
                for (int col = 0; col < matrix[row].length; col++) {
                    if (!colsWithZero.contains(col)) {
                        if (matrix[row][col] == 0) {
                            rowsWithZero.add(row);
                            colsWithZero.add(col);
                        }

                    }
                }
            }
        }


        // Set rows and columns to zero
        rowsWithZero.forEach(row -> {
            for (int col = 0; col < matrix[row].length; col++) {
                matrix[row][col] = 0;
            }
        });

        colsWithZero.forEach(col -> {
            for (int row = 0; row < matrix.length; row++) {
                int bound = matrix[row].length;
                if (col < bound) {
                    matrix[row][col] = 0;
                }
            }
        });

        ArrayHelper.print2D(matrix);
        return matrix;
    }

    /**
     * Determine if a string is a rotation of another string
     * with only one call to substring method.
     * "waterbottle, erbottlewat, true",
     * "taco cat, co catta, true",
     * "happybirthday, birthayhappy, false"
     */
    public static boolean isStringRotation(String s1, String s2) {
        // Sorting and comparing should give the correct result;
        char[] chars1 = s1.toCharArray();
        Arrays.sort(chars1);

        char[] chars2 = s2.toCharArray();
        Arrays.sort(chars2);

        boolean result = Arrays.equals(chars1, chars2);
        return result;
    }

    public static int sumHourGlass(List<List<Integer>> arr) {
        ArrayHelper.pint2D(arr);
        final int MATRIX_SIZE = 6;
        final int HOUR_GLASS_OFFSET = 2;
        final int MAX_BOUND = MATRIX_SIZE - HOUR_GLASS_OFFSET;
        if (arr.isEmpty()) {
            return 0;
        }

        if (arr.size() != MATRIX_SIZE || arr.get(0).size() != MATRIX_SIZE) {
            return 0;
        }

        int maxSum = Integer.MIN_VALUE;
        // Iterate over hour glasses
        for (int row = 0; row < MAX_BOUND; row++) {
            for (int col = 0; col < MAX_BOUND; col++) {
                int sum = sumHR(arr, HOUR_GLASS_OFFSET, row, col);
                maxSum = Math.max(sum, maxSum);
            }
        }

        return maxSum;
    }

    public static int sumHR(final List<List<Integer>> matrix,
                            final int hourGlassOffset,
                            final int topLeftBoundX,
                            final int topLeftBoundY) {

        int sum = 0;
        for (int row = topLeftBoundX; row <= topLeftBoundX + hourGlassOffset; row++) {
            for (int col = topLeftBoundY; col <= topLeftBoundY + hourGlassOffset; col++) {
                if ((row == topLeftBoundX + 1 && col == topLeftBoundY) ||
                        row == topLeftBoundX + 1 && col == topLeftBoundY + hourGlassOffset) {
                    continue;
                }
                sum += matrix.get(row).get(col);
            }
        }

        return sum;
    }

    // I know the matrix will be 3x3 so I only need the top left x and y
    public static boolean checkBounds(final List<List<Integer>> matrix,
                                      final int hourGlassOffset,
                                      final int topLeftBoundX,
                                      final int topLeftBoundY) {
        int matrixMaxIndex = matrix.size() - 1;

        // Check bounds
        return topLeftBoundX + hourGlassOffset <= matrixMaxIndex &&
                topLeftBoundY + hourGlassOffset <= matrixMaxIndex;
    }

    /**
     * It is New Year's Day and people are in line for the Wonderland rollercoaster ride.
     * Each person wears a sticker indicating their initial position in the queue from  to .
     * Any person can bribe the person directly in front of them to swap positions, but they still wear their original sticker.
     * One person can bribe at most two others.
     * <p>
     * Determine the minimum number of bribes that took place to get to a given queue order.
     * Print the number of bribes, or, if anyone has bribed more than two people, print Too chaotic.
     */
    public static String minimumBribes(List<Integer> queue) {
        if (queue.isEmpty()) {
            return Integer.toString(0);
        }
        final String TooChaoticString = "Too chaotic";
        int bribeCount = 0;
        for (int i = 0; i < queue.size(); i++) {
            if (i != queue.get(i) - 1) {
                if (checkMaxBribes(i, queue.get(i) - 1)) {
                    return TooChaoticString;
                } else {
                    bribeCount += getBribeCount(queue, i, queue.get(i));
                }

            }
        }
        return Integer.toString(bribeCount);
    }

    private static int getBribeCount(List<Integer> queue, int newPosition, int originalPosition) {
        int bribeCount = 0;
        for (int i = 0; i < newPosition; i++) {
            if (queue.get(i) > originalPosition) {
                bribeCount++;
            }
        }
        return bribeCount;
    }

    private static boolean checkMaxBribes(int newPosition, int originalPosition) {
        int diff = originalPosition - newPosition;
        return diff > 2;
    }

    public static void minimumBribes1(List<Integer> q) {
        if (q.size() < 2) {
            System.out.println(0);
            return;
        }

        int bribeCount = 0;

        for (int i = 0; i < q.size(); i++) {

            // Check for too many bribes.
            if (q.get(i) > i + 3) {
                System.out.println("Too chaotic");
                return;
            }

//            for (let j = 0; j < i; j++){
//                if (array[j] > array[i]) bribes++
//            }

            int start = Math.max(0, q.get(i) - 2);
            for (int j = start; j < i; j++) {
                if (q.get(j) > q.get(i)) {
                    bribeCount++;
                }
            }
        }
        System.out.println(bribeCount);
    }


    public static String compressStringAlt(String s) {
        final String emptyResult = "Empty String";
        if (s == null || s.isBlank()) {
            return emptyResult;
        }

        char[] charArr = s.toCharArray();
        int dupRightBound = charArr.length - 1;
        int subStringRightBound = dupRightBound;
        for (int i = 0; i < dupRightBound; i++) {
            int dupOffset = i + 1; // The index of the next possible duplicate
            boolean duplicateFound = true; // Indicates is the adjacent char is a dup
            while (duplicateFound &&
                    (dupOffset <= charArr.length - 1)
            ) {
                if (charArr[i] == Character.MIN_VALUE) {
                    if (subStringRightBound == 0) {
                        return emptyResult;
                    } else {
                        return String.valueOf(charArr, 0, i);
                    }
                }
                if (charArr[i] == charArr[dupOffset]) {
                    dupOffset++;
                    duplicateFound = true;
                } else {
                    duplicateFound = false;
                }
            }

            // Delete in range
            if (dupOffset > i + 1) {
                // delete i + 1 -> i + dupOffset
                for (int j = i; j < charArr.length; j++) {
                    int deleteOffset = j + dupOffset;
                    char newChar = Character.MIN_VALUE;
                    if (charArr.length > deleteOffset) { // Pad left with empty char
                        newChar = charArr[deleteOffset];
                    }
                    charArr[j] = newChar;
                }
                i--; // move index back by one to recheck left char after delete

            }
        }
        if (subStringRightBound == 0) {
            return emptyResult;
        } else {
            return String.valueOf(charArr, 0, subStringRightBound);
        }
    }

    public static String compressString(String s) {
        final String emptyResult = "Empty String";
        if (s == null || s.isBlank()) {
            return emptyResult;
        }

        if (s.length() == 1) {
            return s;
        }
        char[] chars = s.toCharArray();
        int index = 0;
        int endPosition = chars.length - 1;
        while (index < chars.length && !isEndOfString(chars, index)) {
            int deleteOffset = index + 1; // Check for deletes starting with next char
            boolean duplicateFound = false;

            // Loop until no duplicates are found
            do {
                if (chars[index] == chars[deleteOffset]) {
                    deleteOffset++; // dup found increate offset to check next
                    duplicateFound = true;
                } else {
                    duplicateFound = false;
                }
            } while (duplicateFound);

            // If deletes were found (deleteOffset > index + 1) delete in range index -> delete offset
            if (deleteOffset > index + 1) {
                for (int j = index; j < chars.length; j++) {
                    int swapIndex = j + deleteOffset;
                    if (isEndOfString(chars, swapIndex)) {
                        chars[j] = Character.MIN_VALUE;
                        endPosition = Math.min(endPosition, j);
                    } else {
                        chars[j] = chars[swapIndex];
                    }
                }
                index--; // recheck for duplicates at current index after delete
            }


            index++;
        }
        String result;
        if (isEndOfString(chars, 0)) {
            result = emptyResult;
        } else {
            result = String.valueOf(chars, 0, endPosition);
        }
        return result;
    }

    private static boolean isEndOfString(char[] charArr, int index) {
        boolean outOfBounds = index >= charArr.length;
        if (!outOfBounds) {
            boolean onTerminalChar = charArr[index] == Character.MIN_VALUE;
            return onTerminalChar;
        }
        return true;
    }

    public static String deleteCharacterPairs(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }


        char[] chars = s.toCharArray();

        int rightBoundForPossibleDuplicates = chars.length - 2; // the last char cannot have a dup to the right/
        int endOfString = chars.length - 1;
        // Iterate over chars
        for (int index = 0; index <= rightBoundForPossibleDuplicates; index++) {
            int pairOffset = index + 1;
            if (chars[index] == chars[pairOffset]) {
                if (chars[index] == Character.MIN_VALUE) {
                    endOfString = Math.min(endOfString, index); // Keep track of the end of the string so we can return a sub string at the end
                    // End of String reached
                    break;
                }
                // Delete and shift;
                for (int shiftIndex = index; shiftIndex < chars.length; shiftIndex++) {
                    int swapIndex = shiftIndex + 2;
                    char newChar;
                    if (swapIndex >= chars.length) {  // If index exceeds bounds insert empty char.
                        newChar = Character.MIN_VALUE;
                    } else {
                        newChar = chars[swapIndex];
                    }
                    chars[shiftIndex] = newChar;
                }
                index = index - 2 >= -1 ? index - 2 : index - 1; // A new pair is possible after a delete, recheck previous position;
            }

        }

        if (chars[0] == Character.MIN_VALUE) {
            return "Empty String";
        } else {
            return String.valueOf(chars, 0, endOfString); // truncate empty string characters
        }
    }

    public static int passwordStr(String password, int n) {
        final int minLen = 6;
        int lengthNeeded = Math.max(minLen - password.length(), 0);
        int conditionsNeeded = 4;
        boolean missingLower = true;
        boolean missingUpper = true;
        boolean missingDigit = true;
        boolean missingSpecl = true;


        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isLowerCase(c)) {
                if (missingLower) {
                    conditionsNeeded--;
                    missingLower = false;
                }
            } else if (Character.isUpperCase(c)) {
                if (missingUpper) {
                    conditionsNeeded--;
                    missingUpper = false;
                }
            } else if (Character.isDigit(c)) {
                if (missingDigit) {
                    conditionsNeeded--;
                    missingDigit = false;
                }
            } else if (!Character.isWhitespace(c)) {
                if (missingSpecl) {
                    conditionsNeeded--;
                    missingSpecl = false;
                }
            }

            if (conditionsNeeded == 0) {
                // All conditions but length met
                return lengthNeeded;
            }
        }

        if (lengthNeeded > conditionsNeeded) {
            return lengthNeeded;
        } else {
            return conditionsNeeded;
        }
    }

    // "beabeefeab, 5",
    public static int longestAlternatingPairs(String s) {
        List<Character> nonContingent = removeContingent(s);
        HashSet<Character> distinctChars = getUniqueChars(nonContingent);
        AtomicInteger max = new AtomicInteger();
        distinctChars.forEach(c -> {
            List<Character> temp = deleteChar(nonContingent, c);
            if (isAlternating(temp)){
                max.set(Math.max(max.get(), temp.size()));

            }
        });


        return max.get();
    }

    private static List<Character> deleteChar(List<Character> chars, char c) {
        List<Character> copy = new ArrayList<>(chars);
        copy.removeIf(i -> (i == c));
        return copy;
    }

    private static boolean isAlternating(List<Character> chars) {
        if (chars.size() < 2) {
            return false;
        }
        char first = chars.get(0);
        char second = chars.get(1);
        for (int i = 0; i < chars.size(); i++) {
            if (chars.get(i) != first) {
                return false;
            }
            if (i + 1 < chars.size()) {
                if (chars.get(i + 1) != second) {
                    return false;
                }
            }
            char temp = second;
            second = first;
            first = temp;
        }
        return true;
    }

    private static List<Character> removeContingent(String s) {
        List<Character> toDelete = new ArrayList<>();
        List<Character> chars = s.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                toDelete.add(s.charAt(i));
                i++;
            }
        }

        for (Character c : toDelete) {
            chars.removeIf(c::equals);
        }
        return chars;
    }

    private static HashSet<Character> getUniqueChars(List<Character> chars) {
        HashSet<Character> distinct = new HashSet<>();
        distinct.addAll(chars);
        return distinct;
    }

    final static char[] ABC = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static String caesarCipher(String s, int k){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isLetter(c)) {
                sb.append(c);
            } else {
                boolean isUpper = Character.isUpperCase(c);
                char newChar = ABC[getEncodedIndex(Character.toLowerCase(c), k)];

                sb.append(isUpper ? Character.toUpperCase(newChar) : newChar);
            }
        }
        return sb.toString();
    }

    public static String caesarCipherV2(String s, int k){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isLetter(c)) {
                sb.append(c);
            } else {
                char newChar = getEncodedIndexV2(c, k);

                sb.append(newChar);
            }
        }
        return sb.toString();
    }

    private static int getEncodedIndex(char c, int k){
        for (int i =0; i < ABC.length; i++) {
            if (ABC[i] == c) {
                return (i+k) % ABC.length;
            }
        }
        return 0;
    }

    private static char getEncodedIndexV2(char c, int k){
        int lowerCaseLowerBound = 'a';
        int lowerCaseUpperBound = 'z';
        int length = lowerCaseUpperBound - lowerCaseLowerBound;
        int upperCaseLowerBound = 'a';
        int upperCaseUpperBound = 'Z';
        int offset;
        int overflow = lowerCaseUpperBound + (c + k) - lowerCaseLowerBound;
        if (Character.isLowerCase(c)) {
            if (c+k > lowerCaseUpperBound) {
                offset = (c+k) - length;
            } else {
                offset = c + k;
            }
        } else {
            if (c+k > upperCaseUpperBound) {
                offset = upperCaseLowerBound + overflow;
            } else {
                offset = c + k;
            }
        }

        return (char) offset;
    }


    /**
     * Palindromes are strings that read the same from the left or right, for example madam or 0110.
     *
     * You will be given a string representation of a number and a maximum number of changes you can make.
     * Alter the string, one digit at a time, to create the string representation of the largest number possible
     * given the limit to the number of changes. The length of the string may not be altered, so you must consider 's
     * left of all higher digits in your tests. For example  is valid,  is not.
     *
     * Given a string representing the starting number, and a maximum number of changes allowed, create
     * the largest palindromic string of digits possible or the string '-1' if it is not possible to create
     * a palindrome under the contstraints.
     * @param str
     * @param strLength
     * @param maxChanges
     * @return
     */
    public final static String INVALID_RESPONSE = "-1";
    public final static char MAX_DIGIT = '9';
    public static String highestValuePalindrome(String str, int strLength, int maxChanges) {
        if (str == null ) {
            return  INVALID_RESPONSE;
        }
        if (strLength == 1) {
            return String.valueOf(9);
        }
        StringBuilder sb = new StringBuilder(str);
        int minChangesNeeded = 0;
        int layers = strLength / 2; // a pair of opposite charters, ex. the 1st and last characters are a layer
        int allowedOverwrites = 0;
        int rightIndexBound = strLength - 1;
        boolean isOdd = strLength % 2 != 0;

        // Get required edits, return if its > maxEdits
        for (int leftIndex = 0; leftIndex < layers; leftIndex++) {
            int rightIndex = rightIndexBound - leftIndex;
            if (sb.charAt(rightIndex) != sb.charAt(leftIndex)) {
                minChangesNeeded++;
                if (minChangesNeeded > maxChanges) {
                    return INVALID_RESPONSE;
                }
            }
        }

        // Edge case only middle can be updated
        if (minChangesNeeded == 0 && maxChanges == 1 && isOdd) {
            sb.setCharAt(layers, MAX_DIGIT);
            return sb.toString();
        }

        // Not enough edits
        if (minChangesNeeded > maxChanges) {
            return INVALID_RESPONSE;
        }

        allowedOverwrites = maxChanges - minChangesNeeded;

        for (int leftIndex = 0; leftIndex < layers; leftIndex++) {
            int rightIndex = rightIndexBound - leftIndex;
            // Best chase left most characters are 9, check it layer can be converted to 9s
            // with one change, if not check 2;
            char left = sb.charAt(leftIndex);
            char right = sb.charAt(rightIndex);
            char maxChar = left > right ? left : right;

            // Special Cases
            if (left == right && left == MAX_DIGIT) {
                continue;
            }
            if (left == right && allowedOverwrites <= 1 ) {
                continue;
            }

            // Set to 9 in once edit
            if (left == MAX_DIGIT) {
                sb.setCharAt(rightIndex, MAX_DIGIT);
                maxChanges--;
                minChangesNeeded--;
            } else if (right == MAX_DIGIT) {
                sb.setCharAt(leftIndex, MAX_DIGIT);
                maxChanges--;
                minChangesNeeded--;
            } else if (allowedOverwrites > 0 && maxChanges > 1) {
                sb.setCharAt(rightIndex, MAX_DIGIT);
                maxChanges--;
                sb.setCharAt(leftIndex, MAX_DIGIT);
                maxChanges--;
                allowedOverwrites--;
            }

            // Update to max in layer otherwise

            else if (left == maxChar) {
                sb.setCharAt(rightIndex, left);
                maxChanges--;
                minChangesNeeded--;

            } else {
                sb.setCharAt(leftIndex, right);
                maxChanges--;
                minChangesNeeded--;
            }
        }

        // If possible set middle digit to 9
        if (maxChanges > 0 && isOdd) {
            sb.setCharAt(layers , MAX_DIGIT);
        }
        return sb.toString();
    }

    public static String highestValuePalindromeV2(String str, int strLength, int maxEdits) {
        if (str == null) {
            return INVALID_RESPONSE;
        }
        if (strLength == 1) {
            return String.valueOf(MAX_DIGIT);
        }

        StringBuilder sb = new StringBuilder(str);
        List<Integer> editedIndices = new ArrayList<>(); // keep track of edits made, we only need the left index since the right is parallel
        int leftBound = 0;
        int rightBound = strLength - 1;

        // Make mandatory edits
        // 1221
        // 12321
        // Iterate over left and right side of string, ignore middle if odd length;
        while (leftBound < rightBound) {
            // If characters are not the same, set both the highest
            char left = sb.charAt(leftBound);
            char right = sb.charAt(rightBound);
            if (left != right){
                if (left > right) {
                    sb.setCharAt(rightBound, left);
                } else {
                    sb.setCharAt(leftBound, right);
                }
                editedIndices.add(leftBound);
                maxEdits--;
            }

            // If max edits are exceeded, exit
            if (maxEdits < 0) {
                return INVALID_RESPONSE;
            }
            leftBound++;
            rightBound--;
        }

        // String should be a palindrome now, if no more edits are allowed, return;
        if (maxEdits == 0) {
            return sb.toString();
        }

        leftBound = 0;
        rightBound = strLength - 1;
        while (leftBound <= rightBound && maxEdits > 0) {

            // Edge case if middle reached with edits, set to 9
            if (leftBound == rightBound && maxEdits > 0) {
                sb.setCharAt(leftBound, MAX_DIGIT);
            }

            char left = sb.charAt(leftBound);
            boolean alreadyEdited = editedIndices.contains(leftBound);
            if (alreadyEdited &&
                left != MAX_DIGIT  &&
                maxEdits > 0) {
                sb.setCharAt(leftBound, MAX_DIGIT);
                sb.setCharAt(rightBound, MAX_DIGIT);
                maxEdits--;
            }
            else if (left != MAX_DIGIT &&
                     maxEdits > 1 ) {
                sb.setCharAt(leftBound, MAX_DIGIT);
                sb.setCharAt(rightBound, MAX_DIGIT);
                maxEdits -= 2;
            }

            leftBound++;
            rightBound--;
        }

        return sb.toString();
    }

    public static List<String> weightedUniformStrings(String s, List<Integer> queries) {
        // Write your code here
        HashSet<Integer> answers = new HashSet<>();
        final HashMap<Character, Integer> weightsMap = buildCharWightLookup();

        // Loop over String
        for (int index = 0; index < s.length(); ){

            // loop over possible contiguous chars.
            boolean contiguousFound = false;
            int cursor = index + 1;
            char c = s.charAt(index);
            do {
                int weight = weightsMap.get(c) * (cursor - index); // weight * char occurrences
                answers.add(weight);
                if (cursor <= s.length() -1 && c == s.charAt(cursor)) {
                    contiguousFound = true;
                    cursor++;
                } else {
                    contiguousFound = false;
                }
            } while (contiguousFound);

            // If weight in queries add to answers
            index = cursor;
        }


        List<String> a = new ArrayList<>() {};
        for (Integer query : queries) {
            if (answers.contains(query)) {
                a.add("Yes");
            } else {
                a.add("No");
            }
        }
        return a;
    }

    private static HashMap<Character, Integer> buildCharWightLookup() {
        HashMap<Character, Integer> lookupMap = new HashMap<>();
        int cursor = 'a';
        int rightBound = 'z' ;
        for (int i = 1; cursor <= rightBound; i++, cursor++) {
            lookupMap.put((char) cursor, i);
        }
        return lookupMap;
    }


    private static final double ANSWER_MODIFIER = Math.pow(10, 9) + 7;
    private static String str;

    public static void initializeMaxPalindromes(String s) {
        // This function is called once before all queries.
        str = s;
    }

    public static List<Double> runMaxPalindromes(int[] leftBounds, int[] rightBounds, String s) {
        List<Double> answers = new ArrayList<>();
        initializeMaxPalindromes(s);
        for (int i = 0; 9 < leftBounds.length; i++) {
            double r = maxPalindromes(leftBounds[i], rightBounds[i]);
            answers.add(r);
        }
        return answers;
    }

    private static int maxPalindromes(int leftBound, int rightBound) {
        char[] subString = str.substring(leftBound, rightBound).toCharArray();
        Map<Character, Long> charCountsMap = getCharacterCount(subString);
        long longestPossiblePalindrome = getMaxPalindromeLength((long) subString.length, charCountsMap.values());
        long leftSideLength = longestPossiblePalindrome / 2;
        boolean hasMiddle = longestPossiblePalindrome % 2 != 0;
        long longestPalindrome = getMaxPalindromeLength(leftSideLength, charCountsMap.values());
        if (hasMiddle) {
            long singletons = 0;  // Performing this calc twice could optimize
            for (Long n : charCountsMap.values()) {
                singletons += n % 2;
            }
            if (singletons > 0) {
                longestPalindrome *= singletons;
            }
        }

        return (int) (longestPalindrome % ANSWER_MODIFIER);
    }

    // (length)! / (charCount_1)!(charCount_2)!...(charCount)n!
    public static long getPermutationsWithRepetition(Collection<Long> charCounts, Long length) {
        long dividend = factorial(length);
        long denominator = 1L;
        for (Long l : charCounts) {
            denominator *= factorial(l);
        }

        return dividend / denominator;
    }

    public static long factorial(long n) {
        if (n > 1) {
            return n * factorial(n -1);
        } else {
            return 1;
        }
    }

    public static HashMap<Character, Long> getCharacterCount(char[] chars) {
        HashMap<Character, Long> map = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1L);
            }
        }
        return map;
    }

    public static long getMaxPalindromeLength(Long numberOfCharacters, Collection<Long> characterCounts) {
        long pairCount = 0L;
        long singletonCount = 0L;
        for (Long n : characterCounts) {
            pairCount += n / 2;
            singletonCount += n % 2;
        }

        for (long i = numberOfCharacters; i > 0; i--) {
            boolean isEven = i % 2 == 0;
            if (isEven && pairCount >= i / 2) {
                return i;
            }
            if (!isEven && pairCount >= i / 2  && singletonCount > 0) {
                return i;
            }
        }

        return 0;
    }
}