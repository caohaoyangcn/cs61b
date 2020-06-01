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
        Deque<Character> d = wordToDeque(word);
        while (d.size() > 1) {
            char head = d.removeFirst();
            char tail = d.removeLast();
            if (checkAToZ(head) && checkAToZ(tail)) {
                if (!cc.equalChars(head, tail)) {
                    return false;
                }
            }
            else {
                throw new IllegalArgumentException("word must include only lower case characters");
            }
        }
        return true;
    }
}
