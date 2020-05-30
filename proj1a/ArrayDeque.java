public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private final int baseIndexFirst = 3;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        @SuppressWarnings("unchecked")
        T[] arr = (T[])new Object[8];
        this.items = arr;
        nextFirst = baseIndexFirst;
        nextLast = baseIndexFirst + 1;
    }

    // TODO: implement copy constructor
    public ArrayDeque(ArrayDeque<T> another) {
        @SuppressWarnings("unchecked")
        int itemLength = another.items.length;
        T[] arr = (T[])new Object[itemLength];
        int currIndex;
        for (int i = 1; i <= another.size; i++) {
            currIndex = (another.nextFirst + i) % itemLength;
            arr[currIndex] = another.items[currIndex];
        }
        this.items = arr;
        this.nextFirst = another.nextFirst;
        this.nextLast = another.nextLast;
        this.size = another.size;
    }

    private void resizingArr(int newLength) {
        @SuppressWarnings("unchecked")
        T[] arr = (T[])new Object[newLength];

        int currLength = items.length;
        for (int count = 1; count <= size; count++) {
            int arrIndex = (baseIndexFirst + count) % newLength;
            int itemsIndex = (nextFirst + count) % currLength;
            arr[arrIndex] = items[itemsIndex];
        }
        nextFirst = baseIndexFirst;
        nextLast = (nextFirst + size + 1) % newLength;
        this.items = arr;
    }

    private void checkUsageFactor() {
        int currLength = items.length;
        if (nextFirst == nextLast) {
            resizingArr(2 * currLength);
            return;
        }
        if (size < currLength * 0.25 && currLength >= 16) {
            resizingArr(currLength / 2);
        }
    }

    public int size() {
        return size;
    }

    /**
     * takes constant time without using any iteration or recursion
     * @param item: element to be added to the front
     */
    public void addFirst(T item) {
        checkUsageFactor();
        items[nextFirst] = item;
        nextFirst -= 1;
        if (nextFirst < 0) {
            nextFirst += items.length;
        }
        size += 1;
    }

    /**
     * takes constant time without using any iteration or recursion
     * @param item: element to be added to the end
     */
    public void addLast(T item) {
        checkUsageFactor();
        items[nextLast] = item;
        nextLast += 1;
        if(nextLast >= items.length) {
            nextLast -= items.length;
        }
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        int currLength = items.length;
        int ptr;
        System.out.print("Front");
        for (int count = 1; count <= size; count++) {
            ptr = (nextFirst + count) % currLength;
            System.out.print(" <--> ");
            System.out.print(items[ptr]);
        }
        System.out.print(" <--> ");
        System.out.println("End");
    }

    /**
     * takes constant time without using any iteration or recursion
     * @return  : element to be popped from the front
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop out any element from an empty list.");
        }
        checkUsageFactor();
        nextFirst = (nextFirst + 1) % items.length;
        size -= 1;
        return items[nextFirst];
    }

    /**
     * takes constant time without using any iteration or recursion
     * @return  : element to be popped from the end
     */
    public T removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop out any element from an empty list.");
        }
        checkUsageFactor();
        nextLast = (nextLast - 1) % items.length;
        size -= 1;
        return items[nextLast];
    }

    /**
     * get index th element of queue using iteration.
     * @param index
     * @return
     */
    public T get(int index) {
        int target = (nextFirst + 1 + index) % items.length;
        return items[target];
    }

}
