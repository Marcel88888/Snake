import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Image;

/**
 * Game board class
 */
class Board extends JPanel implements ActionListener
{
    /**
     * Controller class' object
     */
    private Controller controller;
    /**
     * Image class' object (image of snake's head)
     */
    private Image head;
    /**
     * Image class' object (image of snake's body unit)
     */
    private Image bodyUnit;
    /**
     * Image class' object (image of food)
     */
    private Image food;
    /**
     * Image class' object (image of wall- border of the game board)
     */
    private Image wall;
    /**
     * the width of the game board
     */
    private final int WIDTH = 400;
    /**
     * the height of the game board
     */
    private final int HEIGHT = 370;
    /**
     * the size of the unit of the game board in pixels
     */
    private final int PIXELS_SIZE = 10;

    /**
     * The constructor of the class which initializes the parameters: controller, head, bodyUnit, food, wall, adds
     * a KeyListener, sets the colour of the background of the game board and calls the JPanel's method
     * setFocusable(true)
     * @param controller the Controller class' object
     */
    public Board(Controller controller)
    {
        this.controller = controller;

        addKeyListener(new Adapter());
        setBackground(Color.white);

        ImageIcon h = new ImageIcon(this.getClass().getResource("head.jpg"));
        this.head = h.getImage();

        ImageIcon b = new ImageIcon(this.getClass().getResource("body.jpg"));
        this.bodyUnit = b.getImage();

        ImageIcon f = new ImageIcon(this.getClass().getResource("food.jpg"));
        this.food = f.getImage();

        ImageIcon w = new ImageIcon(this.getClass().getResource("wall.jpg"));
        this.wall = w.getImage();

        setFocusable(true);
    }

    /**
     * @return the size of the unit of the game board in pixels
     */
    public int getPIXELS_SIZE() { return PIXELS_SIZE; }

    /**
     * @return the height of the game board
     */
    public int getHEIGHT() { return HEIGHT; }

    /**
     * @return the width of the game board
     */
    public int getWIDTH() { return WIDTH; }

    /**
     * Calls the Controller class' object's method control().
     * @param e ActionEvent class' object
     */
    public void actionPerformed(ActionEvent e) { controller.control(); }

    /**
     * Paints the game board, snake and food on the basis of the data in the Model.
     * @param g Graphics class' object
     */
    public void paint(Graphics g)
    {
        super.paint(g);

        if (controller.isStatus())
        {
            g.drawImage(food, controller.readFoodX(), controller.readFoodY(), this);
            for (int i=0; i<Snake.getSize(); i++)
            {
                if (i == 0)
                    g.drawImage(head, controller.readX().get(i), controller.readY().get(i), this);
                else
                    g.drawImage(bodyUnit, controller.readX().get(i), controller.readY().get(i), this);
            }
        }
        for (int i=0; i<=WIDTH-PIXELS_SIZE; i+=PIXELS_SIZE)
        {
            g.drawImage(wall, i, 0, this);
            g.drawImage(wall, i, HEIGHT-PIXELS_SIZE, this);
        }
        for (int i=PIXELS_SIZE; i<HEIGHT-PIXELS_SIZE; i+=PIXELS_SIZE)
        {
            g.drawImage(wall, 0, i, this);
            g.drawImage(wall, WIDTH-PIXELS_SIZE, i, this);
        }
    }

    /**
     * Inner class for listening events concerning pressing the keys of the keyboard by the player.
     */
    private class Adapter extends KeyAdapter
    {
        /**
         * After pressing the key by the player calls the controller's methods which call the Snake class' object's
         * methods which change the direction of the snake's movement.
         * @param e KeyEvent class' object
         */
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !controller.checkIsRight())
            {
                controller.orderToTurn(Directions.LEFT);
            }
            if (key == KeyEvent.VK_RIGHT && !controller.checkIsLeft())
            {
                controller.orderToTurn(Directions.RIGHT);
            }
            if (key == KeyEvent.VK_UP && !controller.checkIsDown())
            {
                controller.orderToTurn(Directions.UP);
            }
            if (key == KeyEvent.VK_DOWN && !controller.checkIsUp())
            {
                controller.orderToTurn(Directions.DOWN);
            }
        }
    }
}
