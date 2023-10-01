package org.rasedgwick.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
     */
    public static String SimpleStringCompression(String str) {
        if (str == null || str.isBlank()) {
            return "";
        }

        return "";
    }


}
