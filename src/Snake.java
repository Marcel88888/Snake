import java.util.ArrayList;

/**
 * Stores the data about the state of the game.
 */
class Snake
{
    /**
     * The object of class Controller
     */
    private Controller controller;
    /**
     * The current size of the snake
     */
    private static int size;
    /**
     * The current score of the player
     */
    private static int score = 0;
    /**
     * The x coordinate of the food location
     */
    private int food_x;
    /**
     * The y coordinate of the food location
     */
    private int food_y;
    /**
     * The lists of coordinates of snake's body units' location
     */
    private ArrayList<Integer> x, y;
    /**
     * The boolean if the snake is going up
     */
    private boolean up = false;
    /**
     * The boolean if the snake is going down
     */
    private boolean down = false;
    /**
     * The boolean if the snake is going left
     */
    private boolean left = true;
    /**
     * The boolean if the snake is going right
     */
    private boolean right = false;

    /**
     * The constructor of the class initializing values of the fields: controller, x, y and size
     * @param controller Object of the Controller class
     */
    public Snake(Controller controller) {
        this.controller = controller;
        this.x = new ArrayList<>();
        this.y = new ArrayList<>();
        size = 3;
    }

    /**
     * @param sc the current player's score to set
     */
    public static void setScore(int sc) { score = sc; }

    /**
     * @param size the current size of the snake to set
     */
    public static void setSize(int size) { Snake.size = size; }

    /**
     * @return the current score of the player
     */
    public static int getScore() { return score; }

    /**
     * @return the current value of the size of the snake
     */
    public static int getSize() { return size; }

    /**
     * @param up the boolean value of up to set
     */
    public void setUp(boolean up) { this.up = up; }

    /**
     * @param down the boolean value of down to set
     */
    public void setDown(boolean down) { this.down = down; }

    /**
     * @param left the boolean value of left to set
     */
    public void setLeft(boolean left) { this.left = left; }

    /**
     * @param right the boolean value of right to set
     */
    public void setRight(boolean right) { this.right = right; }

    /**
     * @return the boolean value of up
     */
    public boolean isUp() { return up; }

    /**
     * @return the boolean value of down
     */
    public boolean isDown() { return down; }

    /**
     * @return the boolean value of left
     */
    public boolean isLeft() { return left; }

    /**
     * @return the boolean value of right
     */
    public boolean isRight() { return right; }

    /**
     * @return the list of x coordinates of the snake's body units' location
     */
    public ArrayList<Integer> getX() { return x; }

    /**
     * @return the list of y coordinates of the snake's body units' location
     */
    public ArrayList<Integer> getY() { return y; }

    /**
     * @return the x coordinate of the current food location
     */
    public int getFoodX() { return food_x; }

    /**
     * @return the y coordinate of the current food location
     */
    public int getFoodY() { return food_y; }

    /**
     * Clears the lists of x and y coordinates of snake's body units' location.
     */
    public void clearLists()
    {
        x.clear();
        y.clear();
    }

    /**
     * Makes the initials snake's body units.
     */
    public void makeFirstBodyUnits()
    {
        for (int i=0; i<size; i++)
        {
            x.add(180 + i*controller.readPIXELS_SIZE());
            y.add(180);
        }
    }

    /**
     * Changes the x and y coordinates of snake's body units according to the current direction of snake's movement.
     */
    public void move()
    {
        int tmp;
        for (int i = size-1; i > 0; i--)
        {
            x.set(i, x.get(i-1));
            y.set(i, y.get(i-1));
        }
        if (right)
        {
            tmp = x.get(0);
            x.set(0, tmp+controller.readPIXELS_SIZE());
        }
        if (left)
        {
            tmp = x.get(0);
            x.set(0, tmp-controller.readPIXELS_SIZE());
        }
        if (up)
        {
            tmp = y.get(0);
            y.set(0, tmp-controller.readPIXELS_SIZE());
        }
        if (down)
        {
            tmp = y.get(0);
            y.set(0, tmp+controller.readPIXELS_SIZE());
        }
    }

    /**
     * Draws the new x and y coordinates of food location and check if the new value is not the same as the old one.
     */
    public void locateFood()
    {
        boolean tmp = true;
        while (tmp)
        {
            int t = (int) (Math.random()* ((controller.readWIDTH() - 2 * controller.readPIXELS_SIZE()) / 10 ) + 1);
            food_x = t * controller.readPIXELS_SIZE();
            t = (int) (Math.random()* ((controller.readHEIGHT() - 2 * controller.readPIXELS_SIZE()) / 10 ) + 1);
            food_y = t * controller.readPIXELS_SIZE();

            if (!x.contains(food_x) && !y.contains(food_y))
                tmp = false;
        }
    }

    /**
     * Checks if the snake did not go out from the board or did not hit himself.
     */
    public void checkCollision()
    {
        for (int i=1; i<size; i++)
        {
            if(x.get(i).equals(x.get(0)) && y.get(i).equals(y.get(0)))
            {
                controller.setStatus(false);
                break;
            }
        }

        if (x.get(0) > controller.readWIDTH()-2*controller.readPIXELS_SIZE())
            controller.setStatus(false);

        if (x.get(0) < controller.readPIXELS_SIZE())
            controller.setStatus(false);

        if (y.get(0) > controller.readHEIGHT()-2*controller.readPIXELS_SIZE())
            controller.setStatus(false);

        if (y.get(0) < controller.readPIXELS_SIZE())
            controller.setStatus(false);
    }

    /**
     * Checks if the current location of head of the snake is the same as the location of the food. If so, the function
     * increments the size of the snake, ups the score by 10 points, informs the controller about that, adds new body
     * unit and locates new food.
     */
    public void checkFood()
    {
        if (x.get(0) == food_x && y.get(0) == food_y)
        {
            ++size;
            score += 10;
            controller.orderToChangeDisplayedScore();
            x.add(x.get(1));
            y.add(y.get(1));
            locateFood();
        }
    }
}
