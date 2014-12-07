package application;

import java.util.HashSet;
import java.util.Set;

/**
 * @author vladimir
 */
public class TaskLibrary {

    public Set<Integer> duplicateNumbers(int [] array) {
        Set<Integer> duplicateNumbers = new HashSet<Integer>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[i] == array[j] && i != j) {
                    duplicateNumbers.add(array[i]);
                }
            }
        }
        return duplicateNumbers;
    }

    /**
     * Reverses the word.
     *
     * @param word which should be reversed
     * @return dalv if the input was vlad
     */
    public String reverseWord(String word) {
        StringBuilder reverseWord = new StringBuilder();
        for (int i = word.length() - 1; i >= 0; i--) {
            char character = word.charAt(i);
            reverseWord.append(character);
        }
        return reverseWord.toString();
    }
}
