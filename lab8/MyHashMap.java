import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V>{
    private class HashNode<K, V> {
        K key;
        V val;
        HashNode<K, V> next;

        public HashNode(K k, V v) {
            this.key = k;
            this.val = v;
        }
    }
    private static final int INIT_CAPACITY = 16;
    private static final double DEFAULT_LOADFACTOR = .75;
    private HashSet<K> keys;
    private ArrayList<HashNode<K, V>> vals;
    private int capacity;       // number of buckets
    private final double loadFactor;
    private int size;       // number of key-value pairs
    private final int initialize;

    public Iterator<K> iterator() {
        return keys.iterator();
    }

    public MyHashMap(int initialize, double loadFactor) {
        this.initialize = initialize;
        capacity = initialize;
        keys = new HashSet<>();
        this.loadFactor = loadFactor;
        vals = new ArrayList<>(initialize);
        for(int i = 0; i < initialize; i++) {
            vals.add(null);
        }
        size = 0;
    }

    public MyHashMap(int initialize) {
        this(initialize, DEFAULT_LOADFACTOR);
    }

    public MyHashMap() {
        this(INIT_CAPACITY, DEFAULT_LOADFACTOR);
    }

    public void clear() {
        vals = new ArrayList<>(initialize);
        for (int i = 0; i < initialize; i++) {
            vals.add(null);
        }
        keys = new HashSet<>();
        size = 0;
        capacity = initialize;
    }

    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    public V get(K key) {
        if (key == null) {
            return null;
        }
        if (keys.contains(key)) {
            HashNode<K, V> root = vals.get(hashCode(key));
            while (!key.equals(root.key)) {
                root = root.next;
            }
            return root.val;
        }
        return null;
    }

    public int size() {
        return size;
    }

    private void resize(int newCapacity) {
        MyHashMap<K, V> temp = new MyHashMap<>(newCapacity);
        for (K key: keys) {
            temp.put(key, this.get(key));
        }
        vals = temp.vals;
        size = temp.size;
        capacity = temp.capacity;
    }
    
    private int hashCode(K key) {
        return Math.floorMod(key.hashCode(), capacity);
    }

    public void put(K key, V val) {
        if ((double) size / capacity >= loadFactor) {
            resize(capacity * 2);
        }
        HashNode<K, V> root = vals.get(hashCode(key));
        if (!keys.contains(key)) {
            keys.add(key);
            size += 1;
            if (root == null) {
                vals.set(hashCode(key), new HashNode<K, V>(key, val));
            }
            else {
                while (root.next != null) {
                    root = root.next;
                }
                root.next = new HashNode<K, V>(key, val);
            }
        }
        else {
            while (!root.key.equals(key)) {
                root = root.next;
            }
            root.val = val;
        }
    }

    public Set<K> keySet() {
        return keys;
    }

    public V remove(K key) {
        if (keys.contains(key)) {
            HashNode<K, V> root = vals.get(hashCode(key));
            if (root.key.equals(key)) {
                vals.set(hashCode(key), root.next);
            }
            else {
                HashNode<K, V> predecessor = root;
                while (!root.key.equals(key)) {
                    predecessor = root;
                    root = root.next;
                }
                predecessor.next = root.next;
            }
            keys.remove(key);
            return root.val;
        }
        return null;
    }

    public V remove(K key, V val) {
        if (keys.contains(key)) {
            HashNode<K, V> root = vals.get(hashCode(key));
            if (root.key.equals(key)) {
                vals.set(hashCode(key), root.next);
            }
            else {
                HashNode<K, V> predecessor = root;
                while (!root.key.equals(key)) {
                    predecessor = root;
                    root = root.next;
                }
                predecessor.next = root.next;
            }
            keys.remove(key);
            return val;
        }
        return null;
    }
}
