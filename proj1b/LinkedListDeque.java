public class LinkedListDeque<T> implements Deque<T> {

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
        Node currNode = sentinel;
        while (!ptr.next.equals(other.sentinel)) {
            ptr = ptr.next;
            Node nodeToCopy = new Node(ptr.item, null, null);
            nodeToCopy.prev = currNode;
            currNode.next = nodeToCopy;
            currNode = nodeToCopy;
        }
        sentinel.prev = currNode;
        currNode.next = sentinel;
        size = other.size;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * takes constant time without using any iteration or recursion
     * @param item: element to be added to the front
     */
    @Override
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
    @Override
    public void addLast(T item) {
        Node nodeToAdd = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = nodeToAdd;
        sentinel.prev = nodeToAdd;
        size += 1;
    }

    @Override
    public void printDeque() {
        Node ptr = sentinel;
        System.out.print("Sentinel <--> ");
        while (!ptr.next.equals(sentinel)) {
            ptr = ptr.next;
            System.out.print(ptr.item);
            System.out.print(" <--> ");
        }
        System.out.println("Same sentinel");
    }

    /**
     * takes constant time without using any iteration or recursion
     * @return  : element to be popped from the front
     */
    @Override
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
    @Override
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
    @Override
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
