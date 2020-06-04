package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T>{
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    private int cap;

    private class ArrayRingBufferIterator implements Iterator<T> {

        private int index = first;
        private int incremental = 0;

        public boolean hasNext() {
            return incremental != fillCount;
        }

        public T next() {
            T res = rb[index];
            index = (index + 1) % cap;
            incremental += 1;
            return res;
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    @Override
    public int capacity() {
        return cap;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }
    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // Create new array with capacity elements.
        // first, last, and fillCount should all be set to 0.
        rb = (T[]) new Object[capacity];
        fillCount = 0;
        first = 0;
        last = 0;
        cap = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
        if (other.fillCount() != fillCount) {
            return false;
        }

        int tempFirst = first;
        for (T item: other) {
            if (this.peek() != item) {
                return false;
            }
            first = (first + 1) % cap;
        }
        first = tempFirst;
        return true;
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if (cap == fillCount) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last] = x;
        last = (last + 1) % cap;
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // Dequeue the first item. Don't forget to decrease fillCount and
        // update first.
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T val = rb[first];
        rb[first] = null;
        first = (first + 1) % cap;
        fillCount -= 1;
        return val;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // Return the first item. None of your instance variables should
        // change.
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        return rb[first];
    }

}
