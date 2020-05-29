public class LinkedListDeque<T> {

    Node sentinel;
    int size;

    private class Node {
        Node prev;
        T item;
        Node next;

        public Node (T content, Node prev, Node next) {
            this.prev = prev;
            item = content;
            this.next = next;
        }

    }

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * creates a deep copy of the given deque
     * @param other
     */
    public LinkedListDeque(LinkedListDeque other) {
        Node ptr = other.sentinel;
        sentinel = new Node(null, null, null);

    }

    /**
     * takes constant time without using any iteration or recursion
     * @param item: element to be added to the front
     */
    public void addFirst(T item) {
        Node nodeToAdd = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = nodeToAdd;
        sentinel.next = nodeToAdd;
        size += 1;
    }

    /**
     * takes constant time without using any iteration or recursion
     * @param item: element to be added to the end
     */
    public void addLast(T item) {
        Node nodeToAdd = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = nodeToAdd;
        sentinel.prev = nodeToAdd;
        size += 1;
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
        Node nodeToRemove = sentinel.next;
        nodeToRemove.next.prev = sentinel;
        sentinel.next = nodeToRemove.next;
        size -= 1;
        return nodeToRemove.item;
    }

    /**
     * takes constant time without using any iteration or recursion
     * @return  : element to be popped from the end
     */
    public T removeLast() {
        Node nodeToRemove = sentinel.prev;
        nodeToRemove.prev.next = sentinel;
        sentinel.prev = nodeToRemove.prev;
        size -= 1;
        return nodeToRemove.item;
    }

    /**
     * get index th element of queue using iteration.
     * @param index
     * @return
     */
    public T get(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Error: index < 0");
        }
        Node nodeToReturn = sentinel.next;
        while (index != 0) {
            nodeToReturn = nodeToReturn.next;
            index -= 1;
        }
        return nodeToReturn.item;
    }

    private T helperGetRecursive(Node n, int index) {
        if (index == 0) {
            return n.item;
        }
        return helperGetRecursive(n.next, index-1);
    }
    /**
     * same as get, but uses recursion
     * @param index
     * @return
     */
    public T getRecursive(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Error: index < 0");
        }
        return helperGetRecursive(sentinel.next, index);
    }
}
