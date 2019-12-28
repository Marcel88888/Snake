import javax.swing.*;
import java.util.ArrayList;
// TODO WYMIARY
// TODO WSZYSTKIE 10
// TODO GRAFIKI
// TODO DOKUMENTACJA

class Controller {

    private Window window;
    private Board board;
    private Snake snake;

    boolean status = true;
    static Timer timer;
    private final int DELAY = 100;

    public Controller()
    {
        this.snake = new Snake(this);
        this.window = new Window(this);
        this.board = window.getBoard();
        initGame();
    }

    public Snake getSnake() { return snake; }

    public Window getWindow() { return window; };

    public Board getBoard() { return board; }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int readAppleX() {
        return snake.getAppleX();
    }

    public int readAppleY() {
        return snake.getAppleY();
    }

    public ArrayList<Integer> readX() {
        return snake.getX();
    }

    public ArrayList<Integer> readY() {
        return snake.getY();
    }

    public int readScore() { return snake.getScore(); }

    public int readSnakeSize() {
        return Snake.getSize();
    }

    public int readBoardWidth() {
        return board.getWidth();
    }

    public int readBoardHeight() {
        return board.getHeight();
    }

    public boolean checkIsUp() {
        return snake.isUp();
    }

    public boolean checkIsDown() {
        return snake.isDown();
    }

    public boolean checkIsRight() {
        return snake.isRight();
    }

    public boolean checkIsLeft() {
        return snake.isLeft();
    }

    public static void main(String[] args)
    {
        new Controller();
    }

    public void startTimer() { timer.start(); }

    public void stopTimer() {
        timer.stop();
    }

    public void initGame()
    {
        snake.resetScore();
        snake.makeFirstBodyUnits();
        snake.locateFood();
        timer = new Timer(DELAY, board);
    }

    public void orderToResetScore() {snake.setScore(0);}

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
            EndOfGame endOfGame = new EndOfGame(this);
        }
    }

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

    public void orderToChangeDisplayedScore() { Window.setScoreText(snake.getScore()); }
}
