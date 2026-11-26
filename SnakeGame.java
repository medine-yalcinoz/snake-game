import java.util.*;
import java.io.*;

/**
 * Class: SnakeGame
 * Main game class. Contains the game loop, BFS path finding, collision handling,
 * scoring, drawing, and logging.
 */
public class SnakeGame {

    // ==================== CONSTANTS ====================
    static final int WIDTH = 75;              // Grid width (x axis)
    static final int HEIGHT = 10;             // Grid height (y axis)
    static final int SNAKE_COUNT = 2;         // Number of snakes in the game
    static final long TIMEOUT_MS = 10_000;    // Timeout in milliseconds (10 seconds)
    static final int MAX_SPEED = 5;           // Maximum speed value (1 = fastest)
    static final long TICK_MS = 100;          // Duration of one tick in ms
    static int[][] scoreMap = new int[WIDTH][HEIGHT]; // Score value for each cell
    // ==================== LOG ====================
    static PrintWriter logWriter;             // Writer for log.txt

    /**
     * Method: log
     * Parameters:
     *   message - text to write to the log file
     * Returns: void
     *
     * What it does:
     *   Writes the given message to log.txt and flushes immediately so that
     *   the log is up to date even if the program crashes.
     */
    static void log(String message) {
        if (logWriter != null) {
            logWriter.println(message);
            logWriter.flush();
        }
    }

    // ==================== GLOBAL STATE ====================
    static int[] food = new int[2];           // Food coordinates {x, y}
    static int[][] occupancy = new int[WIDTH][HEIGHT]; // -1 = empty, >=0 = snake id
    static List<Snake> snakes = new ArrayList<>(); // All snakes (ArrayList stays)
    static Random rnd = new Random();         // Random generator
    static long startTime;                    // Time when the current round started
    static boolean gameOver = false;          // True when the game has ended

    // ==================== MAIN ====================
    /**
     * Method: main
     * Parameters:
     *   args - command line arguments (not used)
     * Returns: void
     *
     * What it does (step by step):
     *   1. Opens log.txt (deletes it first if it exists).
     *   2. Initializes the game (snakes, food, scores).
     *   3. Starts a daemon thread that listens for 'q' key to quit.
     *   4. Runs the main game loop:
     *        - checks timeout
     *        - for each alive snake, decides if it moves this tick and runs BFS
     *        - moves all snakes that should move
     *        - draws the grid
     *        - checks if all snakes are dead
     *   5. Prints final results and closes the log file.
     */
    public static void main(String[] args) {
        // Open the log file (delete first to start fresh)
        try {
            File logFile = new File("log.txt");
            if (logFile.exists()) {
                logFile.delete();
            }
            logWriter = new PrintWriter(new FileWriter("log.txt"));
        } catch (IOException e) {
            System.out.println("Could not open log.txt: " + e.getMessage());
        }

        log("=== GAME STARTED === " + new Date());

        initialize();

        // Daemon thread: quit when the user presses 'q' + Enter
        Thread quitThread = new Thread(() -> {
            try {
                while (!gameOver) {
                    if (System.in.available() > 0) {
                        int c = System.in.read();
                        if (c == 'q' || c == 'Q') {
                            gameOver = true;
                            log("User quit.");
                            logWriter.close();
                            System.exit(0);
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (Exception ignored) {}
        });
        quitThread.setDaemon(true);
        quitThread.start();

        log("[STARTING] Game loop is starting...");

        // ========== MAIN GAME LOOP ==========
        while (!gameOver) {
            long elapsed = System.currentTimeMillis() - startTime;

            // Ask each alive snake whether it moves this tick and compute its next move
            for (Snake s : snakes) {
         
                int[] nextStep = bfsNextStep(s);
                if (nextStep != null) {
                	s.direction = nextStep;
                } else {
                    // BFS could not find a path -> snake dies
                  
                }
            }

            moveAll();
            draw(elapsed);

            // Check if all snakes are dead
            boolean allDead = true;
            if (allDead) {
                gameOver = true;
                log("ALL SNAKES ARE DEAD! Game over.");
                break;
            }
			// Pause the game loop for TICK_MS milliseconds (100 ms).
			// This controls the game speed: without it, the loop would run
			// thousands of times per second and the snakes would move too fast
			// to follow visually, the screen would flicker, and the CPU would
			// be fully utilized.
            try { Thread.sleep(TICK_MS); } catch (InterruptedException ignored) {}
        }

        printResults();
        log("=== GAME ENDED === " + new Date());
        logWriter.close();
    }

    // ==================== INITIALIZATION ====================
    /**
     * Method: initialize
     * Parameters: none
     * Returns: void
     *
     * What it does (step by step):
     *   1. Fills the occupancy grid with -1 (empty).
     *   2. Creates SNAKE_COUNT snakes at random positions.
     *      - Each snake gets a random speed (1-5).
     *      - Each snake gets a random display character from {'#', '*', '-'}.
     *   3. Generates the first food.
     *   4. Updates the scoring map.
     *   5. Records the start time and logs the initial state.
     *
     * Local variables:
     *   characters - available display characters for snakes
     *   x, y       - random coordinates for placing a snake
     *   speed      - random speed for the new snake
     */
    static void initialize() {
    	
    	
        startTime = System.currentTimeMillis();

        // Log the initial state
        log("=== INITIAL STATE ===");
        log("Grid: " + WIDTH + "x" + HEIGHT);
        log("Food: (" + food[0] + "," + food[1] + ")");
        for (Snake s : snakes) {
            log("Snake " + s.id + " | head: (" + s.head()[0] + "," + s.head()[1]
                + ") | speed: " + s.speed + " | character: " + s.character);
        }
        log("=====================");
    }

    // ==================== FOOD GENERATION ====================
    /**
     * Method: generateFood
     * Parameters: none
     * Returns: void
     *
     * What it does (step by step):
     *   1. Picks a random (x, y) inside the grid.
     *   2. Repeats until an empty cell is found, or up to 30 tries.
     *   3. Stores the result in the global 'food' array.
     */
    static void generateFood() {

    }

    // ==================== SCORE MAP ====================
    /**
     * Method: updateScores
     * Parameters: none
     * Returns: void
     *
     * What it does (step by step):
     *   1. For each cell in the grid:
     *        - if occupied by a snake -> score = 0
     *        - if it is the food cell -> score = 0
     *        - otherwise -> score = 100 * (maxDist - dist) / maxDist
     *          (closer to food = higher score, farther = lower)
     *
     */
    static void updateScores() {

    }

    // ==================== BFS ====================
    /**
     * Method: bfsNextStep
     * Parameters:
     *   snake - the snake whose next move we want to compute
     * Returns:
     *   int[] - direction (dx, dy) for the next move, or null if no path exists
     *
     * What it does (step by step):
     *   1. Gets the snake's head. If it is already on the food, returns null.
     *   2. Runs BFS from the head until it reaches the food:
     *   3. If the food was not reached, returns null.
     *   4. Otherwise, walks backwards from the food to the head,
     *      keeping the cell just before the head ("previous").
     *   5. Returns the direction from the head to "previous".
     *
     * Local variables:
     *   bx, by     - head x, y (shorthand)
     *   previous   - cell just before the head in the final path
     */
    static int[] bfsNextStep(Snake snake) {
      
        int bx = 0, by = 0;
        int[] previous = null;
        int[] head = snake.head();
        bx = head[0];
        by = head[1];
        if(bx == food[0] && by == food[1])
        {
        	return null;
        }
        MyQueue queue = new MyQueue();
        boolean[][] visited = new boolean[WIDTH][HEIGHT];
        int[][]parentX = new int[WIDTH][HEIGHT];
        int[][]parentY = new int[WIDTH][HEIGHT];
        queue.enqueue(new int[] {bx, by});
        visited[bx][by] = true;
        boolean found = false;
        while(!queue.isEmpty())
        {
        	int[] dx = {0, 0, -1, 1};
        	int[] dy = {-1, 1, 0, 0};
        	int[] current = queue.dequeue();
        	if(current[0] == food[0] && current[1] == food[1])
        	{
        		found = true;
        	    break;
        	}
        	for(int i = 0; i<4; i++)
            {
            	int nx = current[0] + dx[i];
            	int ny = current[1] + dy[i];
            	if(nx >= 0 && nx < WIDTH && ny >= 0 && ny < HEIGHT && !visited[nx][ny] && (occupancy[nx][ny] == -1 || (nx == food[0] && ny == food[1])))
            	{
            		visited[nx][ny] = true;
            		parentX[nx][ny] = current[0];
            		parentY[nx][ny] = current[1];
            		queue.enqueue(new int[] {nx, ny});
            	}
            }
        	
        	
        }
        if(!found) 
    	{
    		return null;
    	}
    	int currX = food[0];
    	int currY = food[1];
    	while(!(parentX[currX][currY] == bx && parentY[currX][currY] == by))
    	{
    		int tempX = parentX[currX][currY];
    		currY = parentY[currX][currY];
    		currX = tempX;
    	}
    	previous = new int[]{currX, currY};
        

        // Direction from head to "previous" = the first step of the path
        int dirX = previous[0] - bx;
        int dirY = previous[1] - by;
        return new int[]{dirX, dirY};      
        
    }

    // ==================== MOVEMENT ====================
    /**
     * Method: moveAll
     * Parameters: none
     * Returns: void
     *
     * What it does (step by step):
     *   1. Computes the new head position for each snake that will move.
     *   2. Detects collisions:
     *        - collision with another snake -> both die
     *        - two snakes target the same cell -> both die
     *   3. Marks dead snakes and removes them from the grid.
     *   4. Moves alive snakes:
     *        - adds the new head cell to body
     *        - if food was eaten: do NOT remove the tail (snake grows)
     *        - else: remove the tail (snake keeps the same length)
     *   5. If food was eaten, generates new food, updates scores, resets timer.
     *
     * Local variables:
     *   newHeads  - next head position for each snake (null if not moving)
     *   willDie   - boolean array marking snakes that will die this tick
     *   foodEaten - true if any snake ate the food this tick
     *   eaterSnake- reference to the snake that ate the food (or null)
     */
    static void moveAll() {
        // Step 1: compute new head positions
        int[][] newHeads = new int[snakes.size()][];

        boolean[] willDie = new boolean[snakes.size()];

        // Step 2a: other-snake collision check
        
        // Step 2b: two snakes targeting the same cell -> both die
        
        // Step 3: remove dead snakes from the grid
 
        // Step 4: move alive snakes
        boolean foodEaten = false;
        Snake eaterSnake = null;

        for (int i = 0; i < snakes.size(); i++) {
      
        }

        // Step 5: if food was eaten, spawn new food and reset the timer
        if (foodEaten) {
            log("[FOOD EATEN] Snake " + (eaterSnake != null ? eaterSnake.id : -1)
                + " ate the food. Score: " + (eaterSnake != null ? eaterSnake.score : -1));
         
        }
    }

    // ==================== REMOVE FROM GRID ====================
    /**
     * Method: removeFromGrid
     * Parameters:
     *   s - the snake whose cells should be cleared
     * Returns: void
     *
     * What it does:
     *   For every cell in the snake's body, sets occupancy to -1
     *   (as long as the cell still belongs to this snake).
     *
     */
    static void removeFromGrid(Snake s) {

    }

    // ==================== DRAW ====================
    /**
     * Method: draw
     * Parameters:
     *   elapsedMs - elapsed time since the current round started (ms)
     * Returns: void
     *
     * What it does (step by step):
     *   1. Clears the console (uses 'cls' on Windows, 'clear' elsewhere).
     *   2. Builds the header:
     *        - title, remaining time, food position, per-snake info
     *   3. Draws the bordered grid:
     *        - 'O' for food
     *        - snake's character for each snake cell
     *        - ' ' for empty cells
     *   4. Prints the buffer to the console.
     *   5. Writes per-tick debug info to log.txt.
     *
     * Local variables:
     *   remaining - remaining time until timeout (ms)
     *   sb        - StringBuilder used to build the output
     */
    static void draw(long elapsedMs) {
        // Step 1: clear the console
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // silently ignore
        }

        StringBuilder sb = new StringBuilder();

        // Step 2: header
        long remaining = Math.max(0, TIMEOUT_MS - elapsedMs);
        sb.append("=== SNAKE GAME ===\n");
        sb.append("Time left: ").append(remaining / 1000.0).append(" s\n");
        sb.append("Food: (").append(food[0]).append(",").append(food[1]).append(")\n");
        sb.append("(press q + Enter to quit)\n\n");

        // Step 3: top border
        sb.append("+");
        for (int i = 0; i < WIDTH; i++) sb.append("-");
        sb.append("+\n");

        // Step 3b: grid rows
        for (int y = 0; y < HEIGHT; y++) {
            sb.append("|");
            for (int x = 0; x < WIDTH; x++) {
                if (x == food[0] && y == food[1]) {
                    sb.append("O");
                } else if (occupancy[x][y] != -1) {
                	
                } else {
                    sb.append(" ");
                }
            }
            sb.append("|\n");
        }

        // Step 3c: bottom border
        sb.append("+");
        for (int i = 0; i < WIDTH; i++) sb.append("-");
        sb.append("+\n");

        // Step 4: print to console
        System.out.print(sb);

        // Step 5: log per-tick debug info
        log("[TICK] Time: " + (elapsedMs / 1000.0) + " s");
    }

    // ==================== FINAL RESULTS ====================
    /**
     * Method: printResults
     * Parameters: none
     * Returns: void
     *
     * What it does:
     *   Writes the final state of each snake (id, speed, score, alive/dead)
     *   to the log file.
     */
    static void printResults() {
        log("=== GAME OVER ===");
        for (Snake s : snakes) {
            log("Snake " + s.id +
                " | speed=" + s.speed +
                " | score=" + s.score +
                " | " + (s.alive ? "alive" : "DEAD"));
        }
    }
}