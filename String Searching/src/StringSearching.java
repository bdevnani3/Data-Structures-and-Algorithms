/**
 * A string searching implementation.
 *
 *
 * @author Adway Dhillon
 */

import java.util.ArrayList;
import java.util.List;

public class StringSearching implements StringSearchingInterface {

    @Override
    public List<Integer> boyerMoore(CharSequence pattern, CharSequence text) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern"
                + "is messed up");
        }

        if (text == null) {
            throw new IllegalArgumentException("Text"
                + "is messed up");
        }

        if (text.length() == 0 || pattern.length() > text.length()) {
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> list = new ArrayList<Integer>();

        int[] last = buildLastTable(pattern);

        int pl = pattern.length();
        int i = pl - 1;
        int j = pl - 1;
        int tl = text.length();
        
        do {
            char letter = text.charAt(i);
            if (pattern.charAt(j) == letter) {
                if (j == 0) {
                    list.add(i); // We've found a match!
                    i += pl;
                    j = pl - 1;

                } else { // going from right to left
                    i--;
                    j--;
                }

            } else { 
                i += pl - Math.min(j, 1 + last[letter]);
                j = pl - 1;
            }
        } while (i <= tl - 1);
        
        return list;
    }

    @Override
    public int[] buildLastTable(CharSequence pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Something"
                + "is wrong with your pattern!");
        }

        int[] letter = new int[Character.MAX_VALUE + 1];

        for (int i = 0; i < letter.length; i++) {
            letter[i] = -1;
        }

        for (int j = 0; j < pattern.length(); j++) {
            letter[pattern.charAt(j)] = j;
        }

        return letter;
    }

    @Override
    public int generateHash(CharSequence current, int length) {
        if (current == null || length < 0 || length > current.length()) {
            throw new IllegalArgumentException("Error");
        }
        int hC = 0;
        
        for (int i = 0; i < length; i++) {
            int temp1 = 1;
            for (int j = 1; j <= length - 1 - i; j++) {
                temp1 *= BASE;
            }
            int temp = current.charAt(i) * temp1;
            hC += temp;
        }
        return hC;
    }

    @Override
    public int updateHash(int oldHash, int length, char oldChar, char newChar) {
        int temp = 1;
        
        for (int i = 1; i <= length - 1; i++) {
            temp *= BASE;
        }
        int nH = oldHash - oldChar * temp;
        
        nH *= BASE;
        nH += newChar;
        
        return nH;
    }

    @Override
    public List<Integer> rabinKarp(CharSequence pattern, CharSequence text) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern"
                + "is messed up");
        }

        if (text == null) {
            throw new IllegalArgumentException("Text"
                + "is messed up");
        }

        if (text.length() == 0 || pattern.length() > text.length()) {
            return new ArrayList<Integer>();
        }

        ArrayList<Integer> list = new ArrayList<Integer>();
        int tHash = generateHash(text, pattern.length());
        int pHash = generateHash(pattern, pattern.length());


        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            if (pHash == tHash) {
                int j = 0;
                int n = i;
                while (j < pattern.length()
                    && pattern.charAt(j) == text.charAt(n)) {
                    if (j == pattern.length() - 1) {
                        list.add(i);
                        j++;
                    } else {
                        n++;
                        j++;                        
                    }
                }
            }
            if (i < text.length() - pattern.length()) {
                tHash = updateHash(tHash, pattern.length(),
                    text.charAt(i), text.charAt(i + pattern.length()));
            }
        }
        return list;
    }
}