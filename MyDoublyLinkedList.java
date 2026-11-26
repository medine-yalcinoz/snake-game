/**
 * Class: MyDoublyLinkedList
 *
 * A custom doubly linked list implementation written from scratch.
 * Used to store a snake's body cells. Each cell is an int[] of size 2 (x, y).
 *
 * Why we wrote it ourselves:
 *   - To avoid using Java's built-in LinkedList
 *   - To demonstrate how a doubly linked list works internally
 *
 * Structure:
 *   head -> [node1] <-> [node2] <-> ... <-> [nodeN] <- tail
 *
 * Fields:
 *   head - first node in the list (snake's head)
 *   tail - last node in the list (snake's tail)
 *   size - number of nodes currently in the list
 */
public class MyDoublyLinkedList {

    /**
     * Class: Node (inner class)
     *
     * A single cell of the doubly linked list.
     *
     * Fields:
     *   data - the payload (int[] {x, y})
     *   prev - reference to the previous node (null if this is the head)
     *   next - reference to the next node (null if this is the tail)
     */
    static class Node {
        int[] data;     // Cell coordinates {x, y}
        Node prev;      // Previous node in the list
        Node next;      // Next node in the list

        Node(int[] data) {
            this.data = data;
        }
    }

    private Node head;   // First node (snake's head)
    private Node tail;   // Last node (snake's tail)
    private int size;    // Number of nodes

    /**
     * Constructor: MyDoublyLinkedList
     * Parameters: none
     * Returns: nothing
     *
     * What it does:
     *   Creates an empty list 
     */
    public MyDoublyLinkedList() {


    }

    /**
     * Method: addFirst
     * Parameters:
     *   data - the int[] to insert at the beginning of the list
     * Returns: void
     *
     * What it does (step by step):
     *   1. Creates a new node holding 'data'.
     *   2. If the list is empty:
     *        new node becomes both head and tail
     *   3. Otherwise:
     *     	  Link the new node in front of the current head
     *   4. Increments size.
     *
     */
    public void addFirst(int[] data) {
   
    }

    /**
     * Method: removeLast
     * Parameters: none
     * Returns: int[] - the data of the removed node (or null if list is empty)
     *
     * What it does (step by step):
     *   1. If the list is empty, returns null.
     *   2. Otherwise:
     *         Detach the tail
     *   3. Decrements size and returns the removed data.
     */
    public int[] removeLast() {
        return new int[2];
    }

    /**
     * Method: getFirst
     * Parameters: none
     * Returns: int[] - data of the head node (or null if empty)
     *
     * What it does:
     *   Returns head's data, or null if the list is empty.
     */
    public int[] getFirst() {
        return new int[2];
    }

    /**
     * Method: getLast
     * Parameters: none
     * Returns: int[] - data of the tail node (or null if empty)
     *
     * What it does:
     *   Returns tail's data, or null if the list is empty.
     */
    public int[] getLast() {
    	 return new int[2];
    }

    /**
     * Method: get
     * Parameters:
     *   index - position (0 = head, size-1 = tail)
     * Returns: int[] - data at the given index (or null if out of range)
     * What it does:
     *   Returns the data of the node at that position.
     */
    public int[] get(int index) {
    	return new int[2];
    }

    /**
     * Method: size
     * Parameters: none
     * Returns: int - number of nodes in the list
     */
    public int size() {
        return 0;
    }

    /**
     * Method: isEmpty
     * Parameters: none
     * Returns: boolean - true if the list has no nodes.
     */
    public boolean isEmpty() {
        return false;
    }
}