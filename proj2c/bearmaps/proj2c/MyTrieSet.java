package bearmaps.proj2c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private Node root;

    public MyTrieSet() {
        root = new Node();
    }

    private class Node {
        private HashMap<Character, Node> links;
        private boolean isKey;

        public Node() {
            links = new HashMap<>();
            isKey = false;
        }

        public boolean isKey() {
            return isKey;
        }
    }


    @Override
    public void clear() {
        root = new Node();
    }

    @Override
    public boolean contains(String key) {
        if (key == null) {
            return false;
        }
        int index = 0;
        Node currNode = root;
        int maxDex = key.length();
        while (index < maxDex) {
            if (currNode.links.containsKey(key.charAt(index))) {
                currNode = currNode.links.get(key.charAt(index));
                index++;
            }
            else {
                return false;
            }
        }
        return currNode.isKey;
    }

    @Override
    public void add(String key) {
        int index = 0;
        Node currNode = root;
        int maxDex = key.length();
        char c;
        while (index < maxDex) {
            c = key.charAt(index);
            if (currNode.links.containsKey(c)) {
                currNode = currNode.links.get(c);
            }
            else {
                Node newLink = new Node();
                currNode.links.put(c, newLink);
                currNode = newLink;
            }
            index++;
        }
        currNode.isKey = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        ArrayList<String> res = new ArrayList<>();
        Node currNode = root;
        char c;
        for (int index = 0; index < prefix.length(); index++) {
            c = prefix.charAt(index);
            if (currNode.links.containsKey(c)) {
                currNode = currNode.links.get(c);
            }
            else {
                return res;
            }
        }
        return collectHelper(res, prefix, currNode);
    }

    private List<String> collectHelper(List<String> acc, String s, Node n) {
        if (n.isKey()) {
            acc.add(s);
        }
        for (Character c: n.links.keySet()) {
            acc = collectHelper(acc, s.concat(String.valueOf(c)), n.links.get(c));
        }
        return acc;
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
