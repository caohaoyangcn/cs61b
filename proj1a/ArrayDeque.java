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
    public ArrayDeque(ArrayDeque another) {
        int itemLength = another.items.length;

        @SuppressWarnings("unchecked")
        T[] arr = (T[])new Object[itemLength];

        /* New implementation */
        if (another.nextFirst < another.nextLast) {
            if (another.size + another.nextFirst < itemLength) {
                System.arraycopy(another.items, another.nextFirst+1,
                        arr, another.nextFirst+1, another.size);
            }
            else {
                int step = (itemLength - 1) - nextFirst;
                System.arraycopy((T[]) another.items, another.nextFirst+1,
                        arr, another.nextFirst+1, step);
                System.arraycopy((T[]) another.items, another.nextFirst+1,
                        arr, another.nextFirst+1,
                        (another.size + another.nextFirst) % itemLength);
            }
        }
        else {
            System.arraycopy((T[]) another.items, 0, arr, 0, another.nextLast);
            System.arraycopy((T[]) another.items, another.nextFirst + 1, arr,
                    another.nextFirst + 1, itemLength - 1 - another.nextFirst);
        }
//        /* Old implementation */
//        int currIndex;
//        for (int i = 1; i <= another.size; i++) {
//            currIndex = (another.nextFirst + i) % itemLength;
//            arr[currIndex] = (T) another.items[currIndex];
//        }
        this.items = arr;
        this.nextFirst = another.nextFirst;
        this.nextLast = another.nextLast;
        this.size = another.size;
    }

    /**
     * A helper function that copies all the existing data from old items array
     * to an array of newLength and reassign the instance variable items to this new array.
     * The logical relations among the elements remains unchanged, i. e., the order and
     * head-tail relation are still the same in the new array.
     * nextFirst is always reset to baseIndexFirst after the rearrangement.
     * @param newLength
     */
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

    /**
     * A helper function that checks the usage of memory in our array.
     * When the usage factor is either too small or too big,
     * it will then call another helper function named resizingArr
     * to resize the array.
     */
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
            String msg = "Cannot pop out any element from an empty list.";
            throw new IllegalStateException(msg);
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
            String msg = "Cannot pop out any element from an empty list.";
            throw new IllegalStateException(msg);
        }
        checkUsageFactor();
        nextLast = (nextLast - 1) % items.length;
        size -= 1;
        return items[nextLast];
    }

    /**
     * gets index th element of queue, takes constant time.
     * @param index
     * @return
     */
    public T get(int index) {
        int target = (nextFirst + 1 + index) % items.length;
        return items[target];
    }

}
