/**
 * Class: MyQueue
 *
 * A custom FIFO (First In First Out) queue implementation written from scratch.
 * Used by BFS to explore cells in the correct order.
 *
 * Why we wrote it ourselves:
 *   - To avoid using Java's built-in ArrayDeque / Queue
 *   - To demonstrate how a queue works internally
 *
 * Structure:
 *   front -> [node1] -> [node2] -> ... -> [nodeN] <- rear
 *
 * Fields:
 *   front - node at the front of the queue (next to be removed)
 *   rear  - node at the back of the queue (last inserted)
 *   size  - number of nodes currently in the queue
 */
public class MyQueue {

    /**
     * Class: Node (inner class)
     *
     * A single element of the queue.
     *
     * Fields:
     *   data - the payload (int[] {x, y})
     *   next - reference to the next node (null if this is the rear)
     */
    static class Node {
        int[] data;     // Cell coordinates {x, y}
        Node next;      // Next node in the queue

        Node(int[] data) {
            this.data = data;
        }
    }

    private Node front;   // Node at the front (dequeue from here)
    private Node rear;    // Node at the back (enqueue here)
    private int size;     // Number of nodes

    /**
     * Constructor: MyQueue
     * Parameters: none
     * Returns: nothing
     *
     * What it does:
     *   Creates an empty queue 
     */
    public MyQueue() {
    	this.front = null;
    	this.rear = null;
    	this.size = 0;

    }

    /**
     * Method: enqueue
     * Parameters:
     *   data - the int[] to insert at the back of the queue
     * Returns: void
     *
     * What it does (step by step):
     *   1. Creates a new node holding 'data'.
     *   2. If the queue is empty:
     *        the new node is both front and rear
     *   3. Otherwise:
     *        Attach new node after the current rear
     *   4. Increments size.
     */
    public void enqueue(int[] data) {
    	Node newNode = new Node(data);
    	if(front == null)
    	{
    		this.front = newNode;
    		this.rear = newNode;
    	}
    	else
    	{
    		this.rear.next = newNode;
    	    this.rear = newNode;
    	}
    	this.size++;
    	

    }

    /**
     * Method: dequeue
     * Parameters: none
     * Returns: int[] - data of the removed node (or null if queue is empty)
     *
     * What it does (step by step):
     *   1. If the queue is empty, returns null.
     *   2. Otherwise, remove front data.
     *   3. Decrements size and returns the removed data.
     */
    public int[] dequeue() {
    	
    	if(front == null)
    	{
    		return null;
    		
    	}
    	
    	int[] removedData = front.data;
    	front = front.next;
    	if(front == null)
    	{
    		rear = null;	
    	}
    	size--;
    	return removedData;
    }

    /**
     * Method: peek
     * Parameters: none
     * Returns: int[] - data at the front (without removing it), or null if empty
     *
     * What it does:
     *   Returns front's data, or null if the queue is empty.
     */
    public int[] peek() {
    	if(front == null)
    	{
    		return null;
    	}
    	else
    		return front.data;
    }

    /**
     * Method: isEmpty
     * Parameters: none
     * Returns: boolean - true if the queue has no nodes
     */
    public boolean isEmpty() {
        
        	return front == null;
    }

    /**
     * Method: size
     * Parameters: none
     * Returns: int - number of nodes in the queue
     */
    public int size() {
        return size;
    }
}