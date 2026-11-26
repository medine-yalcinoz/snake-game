
/**
 * Class: Snake
 * Represents a single snake in the game.
 *
 * Fields:
 *   id                - Unique identifier of the snake (0, 1, 2, ...)
 *   body              - Snake's body cells (custom doubly linked list)
 *   speed             - Speed factor 1-5 (1 = moves every tick, 5 = every 5 ticks)
 *   score             - Accumulated score from passed cells and eaten food
 *   alive             - True if the snake is alive, false if dead
 *   direction         - Current direction (dx, dy); {0,0} means not set yet
 *   waitCounter       - Counts ticks until the snake can move again
 *   character         - Display character for this snake (#, *, -)
 *   willMoveThisTick  - True if the snake will move in the current tick
 */
public class Snake {
    int id;                              // Unique identifier of the snake
    MyDoublyLinkedList body = new MyDoublyLinkedList(); // Body cells (custom list)
    int speed;                           // Speed factor 1-5 (1 = fastest, 5 = slowest)
    int score;                           // Accumulated score
    boolean alive = true;                // True if snake is alive
    int[] direction = {0, 0};            // Current direction (dx, dy)
    int waitCounter = 0;                 // Ticks remaining until next move
    char character;                      // Display character (#, *, -)
    boolean willMoveThisTick = false;    // True if snake moves this tick

    /**
     * Constructor: Snake
     * Parameters:
     *   id    - unique identifier of the snake
     *   x     - initial x coordinate of the head
     *   y     - initial y coordinate of the head
     *   speed - speed factor (1-5)
     * Returns: nothing (constructor)
     *
     * What it does:
     *   1. Stores the id and speed.
     *   2. Adds the initial head cell to the body.
     */
    Snake(int id, int x, int y, int speed) {
       
    }

    /**
     * Method: head
     * Parameters: none
     * Returns: int[] - the (x, y) coordinates of the snake's head
     *
     * What it does:
     *   Returns the first cell of the body list (the head).
     */
    int[] head() { 
    	return new int[2];
    }

    /**
     * Method: tail
     * Parameters: none
     * Returns: int[] - the (x, y) coordinates of the snake's tail
     *
     * What it does:
     *   Returns the last cell of the body list (the tail).
     */
    int[] tail() { 
    	return new int[2];
    }
}
