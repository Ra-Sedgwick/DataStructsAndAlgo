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
                sb.replace(i, i+1, encodedWhiteSpace);
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
                charMap.put(ci.current(),  charMap.get(ci.current())  + 1);
            } else {
                charMap.put(ci.current(), 0) ;
            }
            ci.next();
        }

        // Logic can be simplified by making sure no more then one character has an odd count.
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






















}
