import org.junit.Test;

public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> res = new ArrayDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            res.addLast(word.charAt(i));
        }
        return res;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word);
        while (d.size() > 1) {
            if (d.removeFirst() != d.removeLast()) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAToZ (char c) {
        return (int) c <= 122 && (int) c >= 97;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        for (int i = 0; i < word.length(); i++) {
            if (!checkAToZ(word.charAt(i))) {
                throw new IllegalArgumentException("word must contain only lower case characters");
            }
        }
        Deque<Character> d = wordToDeque(word);
        while (d.size() > 1) {
            char head = d.removeFirst();
            char tail = d.removeLast();
            if (!cc.equalChars(head, tail)) {
                return false;
            }
        }
        return true;
    }
}
