/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("../library-sp19/data/words.txt");
        Palindrome palindrome = new Palindrome();
        ArrayDeque<String> res = new ArrayDeque<String>();
        String longest = "";
        for (int i = 0; i < 26; i++) {
            CharacterComparator cc = new OffByN(i);
            ArrayDeque<String> temp = new ArrayDeque<String>();
            while (!in.isEmpty()) {
                String word = in.readString();
                if (word.length() >= minLength && palindrome.isPalindrome(word, cc)) {
                    temp.addLast(word);
                }
            }
            if (temp.size() > res.size()) {
                res = temp;
            }
            for (int j = 0; j < temp.size(); j++) {
                String currStr = temp.get(j);
                if (currStr.length() > longest.length()) {
                    longest = currStr;
                }
            }
        }
        System.out.println(longest);
        System.out.println(res.size());
        res.printDeque();
    }
}
