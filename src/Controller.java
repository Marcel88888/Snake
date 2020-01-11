import javax.swing.*;
import java.util.ArrayList;

/**
 * Manages the information from Model and View and sends it between them. Make decisions of the program.
 */
class Controller
{
    /**
     * Object of the class Board
     */
    private Board board;
    /**
     * Object of the class Snake
     */
    private Snake snake;
    /**
     * The current status of the game: active or not
     */
    static boolean status = true;
    /**
     * Object of the class Timer
     */
    static Timer timer;

    /**
     * The constructor of the class initializing values of the fields: snake, board. Makes new object of the Window
     * class and calls initGame(int delay) method.
     * @param delay delay of the Timer class' object determining the difficulty of the game
     */
    public Controller(int delay)
    {
        this.snake = new Snake(this);
        Window window = new Window(this);
        this.board = window.getBoard();
        initGame(delay);
    }

    /**
     * @return the current status of the game
     */
    public boolean isStatus() { return status; }

    /**
     * @param status the current status of the game to set
     */
    public void setStatus(boolean status) { Controller.status = status; }

    /**
     * @return the x coordinate of the current food location
     */
    public int readFoodX() { return snake.getFoodX(); }

    /**
     * @return the y coordinate of the current food location
     */
    public int readFoodY() { return snake.getFoodY(); }

    /**
     * @return the list of x coordinates of the snake's body units' location
     */
    public ArrayList<Integer> readX() { return snake.getX(); }

    /**
     * @return the list of y coordinates of the snake's body units' location
     */
    public ArrayList<Integer> readY() { return snake.getY(); }

    /**
     * @return the current score of the player
     */
    public int readScore() { return Snake.getScore(); }

    /**
     * @return the size of the unit of the game board in pixels
     */
    public int readPIXELS_SIZE() { return board.getPIXELS_SIZE(); }

    /**
     * @return the width of the game board (Board class object)
     */
    public int readWIDTH() { return board.getWIDTH(); }

    /**
     * @return the height of the game board (Board class object)
     */
    public int readHEIGHT() { return board.getHEIGHT(); }

    /**
     * @return the boolean value of up
     */
    public boolean checkIsUp() { return snake.isUp(); }

    /**
     * @return the boolean value of down
     */
    public boolean checkIsDown() { return snake.isDown(); }

    /**
     * @return the boolean value of right
     */
    public boolean checkIsRight() { return snake.isRight(); }

    /**
     * @return the boolean value of left
     */
    public boolean checkIsLeft() { return snake.isLeft(); }

    /**
     * Main method of the program. Makes new object of the class Controller.
     * @param args args of the main() method
     */
    public static void main(String[] args) { new Controller(100); }

    /**
     * Starts the timer (object of class Timer).
     */
    public void startTimer() { timer.start(); }

    /**
     * Stops the timer (object of class Timer).
     */
    public void stopTimer() { timer.stop(); }

    /**
     * Changes the value of the score to 0 in Model part of the program.
     */
    public void orderToResetScore() { Snake.setScore(0); }

    /**
     * Changes the displayed score of the View part of the program.
     */
    public void orderToChangeDisplayedScore() { Window.setScoreText(Snake.getScore()); }

    /**
     * Initializes the game. Set the initial value of the score for 0, calls Snake class' makeFirstBodyUnits() and
     * locateFood() methods, makes new object of the class Timer and sets its delay parameter.
     * @param delay delay of the Timer class' object determining the difficulty of the game
     */
    public void initGame(int delay)
    {
        Snake.setScore(0);
        snake.makeFirstBodyUnits();
        snake.locateFood();
        timer = new Timer(delay, board);
    }

    /**
     * If the game status is active the method calls the Snake class' object's methods: checkFood(), checkCollision(),
     * move() and Board class' object's method repaint(). If not, the method stops the timer (Timer class' object) and
     * sets all the values describing the state of the game for default to make it possible to start the new game,
     * calls Board class' object's method repaint() and makes the new object of EndOfGame class.
     */
    public void control()
    {
        if (status) {
            snake.checkFood();
            snake.checkCollision();
            snake.move();
            board.repaint();
        } else {
            timer.stop();
            snake.clearLists();
            Snake.setSize(3);
            snake.makeFirstBodyUnits();

            status = true;
            snake.setLeft(true);
            snake.setRight(false);
            snake.setUp(false);
            snake.setDown(false);

            Window.enableStart(true);
            Window.enableHighcores(true);
            board.repaint();
            new EndOfGame(this);
        }
    }

    /**
     * Calls the Snake class' object's methods setUp/setDown/setLeft/setRight to change the current direction of snake's
     * movement.
     * @param dir the current direction of snake's movement
     */
    public void orderToTurn(Directions dir)
    {
        switch(dir) {
            case UP:
                snake.setUp(true);
                snake.setLeft(false);
                snake.setRight(false);
                break;
            case DOWN:
                snake.setDown(true);
                snake.setLeft(false);
                snake.setRight(false);
                break;
            case LEFT:
                snake.setLeft(true);
                snake.setUp(false);
                snake.setDown(false);
                break;
            case RIGHT:
                snake.setRight(true);
                snake.setUp(false);
                snake.setDown(false);
                break;
        }
    }
}
