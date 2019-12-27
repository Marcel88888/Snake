import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Image;

class Board extends JPanel implements ActionListener
{
    private Controller controller;
    private Adapter adapter;

    private Image head;
    private Image bodyUnit;
    private Image food;
    private Image wall;

    private final int HEIGHT = 372;
    private final int WIDTH = 400;

    public Board(Controller controller)
    {
        this.controller = controller;

        addKeyListener(new Adapter());
        setBackground(Color.black);

        ImageIcon h = new ImageIcon(this.getClass().getResource("head.jpg"));
        head = h.getImage();

        ImageIcon b = new ImageIcon(this.getClass().getResource("body.jpg"));
        bodyUnit = b.getImage();

        ImageIcon f = new ImageIcon(this.getClass().getResource("food.jpg"));
        food = f.getImage();

        ImageIcon w = new ImageIcon(this.getClass().getResource("wall.jpg"));
        wall = w.getImage();

        setFocusable(true); // ???????????????????????????????????
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    public void actionPerformed(ActionEvent e) { controller.control(); }

    public void paint(Graphics g)
    {
        super.paint(g);

        if (controller.isStatus())
        {
            g.drawImage(food, controller.readAppleX(), controller.readAppleY(), this);
            for (int i=0; i<Snake.getSize(); i++)
            {
                if (i == 0)
                    g.drawImage(head, controller.readX().get(i), controller.readY().get(i), this);
                else
                    g.drawImage(bodyUnit, controller.readX().get(i), controller.readY().get(i), this);
            }
        }
        for (int i=0; i<=390; i+=10)
        {//TODO ZMIENIC WYMIAR
            g.drawImage(wall, i, 0, this);
            g.drawImage(wall, i, 360, this);
        }
        for (int i=10; i<360; i+=10)
        {//TODO ZMIENIC WYMIAR
            g.drawImage(wall, 0, i, this);
            g.drawImage(wall, 390, i, this);
        }
    }

    private class Adapter extends KeyAdapter
    {
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
