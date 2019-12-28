import java.awt.event.ActionEvent;
import java.util.ArrayList;


class Snake
{
    private Controller controller;

    private static int size;
    private static int score = 0;

    private int apple_x;
    private int apple_y;
    private ArrayList<Integer> x, y;

    private boolean up = false;
    private boolean down = false;
    private boolean left = true;
    private boolean right = false;

    public Snake(Controller controller) {
        this.controller = controller;
        this.x = new ArrayList<Integer>();
        this.y = new ArrayList<Integer>();
        size = 3;
    }

    public void setScore(int sc) { score = sc; }

    public static void setSize(int size) { Snake.size = size; }

    public static int getSize() {
        return size;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public ArrayList<Integer> getX() {
        return x;
    }

    public ArrayList<Integer> getY() {
        return y;
    }

    public int getAppleX() {
        return apple_x;
    }

    public int getAppleY() {
        return apple_y;
    }

    public int getScore()
    {
        return score;
    }

    public void clearLists()
    {
        x.clear();
        y.clear();
    }

    public void actionPerformed(ActionEvent e) { controller.control(); }

    public void makeFirstBodyUnits()
    {
        for (int i=0; i<size; i++)
        {
            x.add(180 + i*10);
            y.add(180);
        }
    }

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
            x.set(0, tmp+10);
        }
        if (left)
        {
            tmp = x.get(0);
            x.set(0, tmp-10);
        }
        if (up)
        {
            tmp = y.get(0);
            y.set(0, tmp-10);
        }
        if (down)
        {
            tmp = y.get(0);
            y.set(0, tmp+10);
        }
    }

    public void locateFood()
    {
        boolean tmp = true;
        while (tmp)
        {
            int t = (int) (Math.random()*38+1);
            apple_x = t * 10;
            t = (int) (Math.random()*35+1);
            apple_y = t * 10;

            if (!x.contains(apple_x) && !y.contains(apple_y))
                tmp = false;
        }
    }

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

        if (x.get(0) > 380) // TODO ZMIENIC ROZMIAR
            controller.setStatus(false);

        if (x.get(0) < 10)
            controller.setStatus(false);

        if (y.get(0) > 350) // TODO ZMIENIC ROZMIAR
            controller.setStatus(false);

        if (y.get(0) < 10)
            controller.setStatus(false);
    }

    public void checkFood()
    {
        if (x.get(0) == apple_x && y.get(0) == apple_y)
        {
            ++size;
            score += 10;
            controller.orderToChangeDisplayedScore();
            x.add(x.get(1));
            y.add(y.get(1));
            locateFood();
        }
    }

    public void resetScore() { score = 0; }
}
