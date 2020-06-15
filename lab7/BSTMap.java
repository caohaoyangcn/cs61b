import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Tree root;

    public BSTMap() {
        root = null;
    }

    private class Tree {
        private K key;
        private V val;
        private Tree left, right;
        private int size;

        public Tree(K key, V val, int size) {
            this.key = key;
            this.val = val;
            left = null;
            right = null;
            this.size = size;
        }
    }

    private V find(Tree T, K key) {
        if (T == null) {
            return null;
        }
        if (T.key.compareTo(key) > 0) {
            return find(T.left, key);
        }
        else if (T.key.compareTo(key) < 0) {
            return find(T.right, key);
        }
        else {
            return T.val;
        }
    }

    private Tree insert(Tree T, K key, V val) {
        if (T == null) {
            return new Tree(key, val, 1);
        }
        if (key.compareTo(T.key) < 0) {
            T.left = insert(T.left, key, val);
        }
        else {
            T.right = insert(T.right, key, val);
        }
        T.size += 1;
        return T;
    }

    /**
     * 找到T中最左节点，移除该节点并将节点值赋予R节点，返回修改后的T
     */
    private Tree swapSmallest(Tree T, Tree R) {
        if (T.left == null) {
            R.key = T.key;
            R.val = T.val;
            return T.right;
        } else {
            T.left = swapSmallest(T.left, R);
            return T;
        }
    }

    private Tree delete(Tree T, K key) {
        if (T == null) {
            return null;
        }
        if (T.key.compareTo(key) > 0) {
            T.left = delete(T.left, key);
        }
        else if (T.key.compareTo(key) < 0) {
            T.right = delete(T.right, key);
        }
        if (T.right == null) {
            return T.left;
        }
        else if (T.left == null) {
            return T.right;
        }
        else {
        // replace T's label with that of T's successor without parent pointer
            T.right = swapSmallest(T.right, T);
        // or: T.left = swapBiggest(T.left, T)
        }
        T.size -= 1;
        return T;
    }

    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    /** Removes all of the mappings from this map. */
    public void clear() {
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return find(root, key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return find(root, key);
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        if (root == null) {
            return 0;
        }
        return root.size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        root = insert(root, key, value);
    }

    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}
