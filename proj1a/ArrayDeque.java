public class ArrayDeque<T> {

    T[] Array;
    int size;

    public ArrayDeque() {
        @SuppressWarnings("unchecked")
        T[] arr = (T[])new Object[8];
        this.Array = arr;
    }

    public int size() {
        return size;
    }

    /**
     * takes constant time without using any iteration or recursion
     * @param item: element to be added to the front
     */
    public void addFirst(T item) {

    }

    /**
     * takes constant time without using any iteration or recursion
     * @param item: element to be added to the end
     */
    public void addLast(T item) {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {

    }

    /**
     * takes constant time without using any iteration or recursion
     * @return  : element to be popped from the front
     */
    public T removeFirst() {

    }

    /**
     * takes constant time without using any iteration or recursion
     * @return  : element to be popped from the end
     */
    public T removeLast() {

    }

    /**
     * get index th element of queue using iteration.
     * @param index
     * @return
     */
    public T get(int index) {

    }

}
