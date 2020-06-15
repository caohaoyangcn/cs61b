package bearmaps;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private int[] pq;
    private int[] qp;
    private PriorityNode[] keys;
    private HashMap<T, Integer> items; // mapping from item to index in keys array
    private static final int DEFAULT_SIZE = 16;
    private final double MIN_LOAD_FACTOR = .25;
    private int size;

    public ArrayHeapMinPQ(int size) {
        keys = new ArrayHeapMinPQ.PriorityNode[size];
        pq = new int[size];
        qp = new int[size];
        Arrays.fill(qp, -1);
        items = new HashMap<>();
        size = 0;
    }

    public ArrayHeapMinPQ() {
        this(DEFAULT_SIZE);
    }

    private void resizing(int newLen) {
        ArrayHeapMinPQ<T> temp = new ArrayHeapMinPQ<>(newLen);
        for (PriorityNode key : keys) {
            if (key != null) {
                temp.add(key.getItem(), key.getPriority());
            }
        }
        this.items = temp.items;
        this.keys = temp.keys;
        this.size = temp.size;
        this.pq = temp.pq;
        this.qp = temp.qp;
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        if (size() == pq.length - 1) {
            resizing(2 * pq.length);
        }
        keys[++size] = new PriorityNode(item, priority);
        pq[size] = size;
        qp[size] = size;
        swim(size);
        items.put(item, size);
    }

    @Override
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return (T) keys[pq[1]].getItem();
    }

    @Override
    public T removeSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (pq.length > 16 && (double) size / pq.length <= MIN_LOAD_FACTOR) {
            resizing(pq.length / 2);
        }
        T res = getSmallest();
        exch(1, size--);
        sink(1);
        keys[pq[size+1]] = null;
        qp[pq[size+1]] = -1;
        items.remove(res);
        return res;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = items.get(item);
        keys[index].setPriority(priority);
        swim(qp[index]);
        sink(qp[index]);
    }

    private void exch(int i, int j) {
        int swapIndex = pq[i];
        qp[pq[i]] = j;
        qp[pq[j]] = i;
        pq[i] = pq[j];
        pq[j] = swapIndex;
    }

    private boolean isLessThan(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) < 0;
    }

    private void swim(int k) {
        while (isLessThan(k, k/2) && k > 1) {
            exch(k, k / 2);
            k /= 2;
        }
    }

    private void sink(int k) {
        while (2*k <= size) {
            int j = 2 * k;
            // 小顶堆性质决定了k节点要与子节点中的较小者比较，
            // 这样才保证了一旦交换，新父节点小于两个子节点
            if (j < size && isLessThan(j+1, j)) {
                j++;
            }
            if (isLessThan(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
