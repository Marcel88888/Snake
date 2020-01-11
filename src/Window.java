import javax.swing.*;
import java.awt.Point;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class inheriting from JFrame class
 */
public class Window extends JFrame
{
    /**
     * The Controller class' object
     */
    private Controller controller;
    /**
     * The Board class' object
     */
    private Board board;
    /**
     * The JButton class' object for the button which starts the game
     */
    private static JButton start;
    /**
     * The JButton class' object for the button which displays highscores
     */
    private static JButton highscores;
    /**
     * The JLabel class' object for displaying the score of the player
     */
    private static JLabel score;

    /**
     * The constructor of the class which sets the title of the window of the game, sets the sizes of the elements of
     * the window, loads the logo of the game and do all the necessary things to display all the elements correctly.
     * @param controller the Controller class' object
     */
    public Window(Controller controller)
    {
        super("Snake");

        this.controller = controller;

        setSize(520, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocation(new Point(650, 300));

        Insets insets = new Insets(1,1,1,1);

        start = new JButton("Start");
        start.setMargin(insets);
        start.setFocusable(false);
        start.setBounds(410, 90, 100, 30);
        start.addActionListener(new StartListener());

        highscores = new JButton("Highscores");
        highscores.setMargin(insets);
        highscores.setFocusable(false);
        highscores.setBounds(410, 130, 100, 30);
        highscores.addActionListener(new ScoreListener());

        JButton endGame = new JButton("Exit");
        endGame.setMargin(insets);
        endGame.setFocusable(false);
        endGame.setBounds(410, 170, 100, 30);
        endGame.addActionListener(new EndGameListener());

        JLabel scoreTxt = new JLabel("Score:");
        scoreTxt.setBounds(415, 30, 45, 30);

        score = new JLabel("0");
        score.setBounds(470, 30, 42, 30);

        String logo_filename_path = "/home/dell/Studia/sem4/PROZ/Snake/src/logo.jpg";
        JLabel logo = new JLabel(new ImageIcon(logo_filename_path));
        logo.setBounds(415, 210, 92, 161); // 401 210 92 161

        this.board = new Board(this.controller);
        board.setBounds(0, 0, board.getWIDTH(), board.getHEIGHT());

        add(board);
        add(start);
        add(highscores);
        add(scoreTxt);
        add(endGame);
        add(logo);
        add(score);

        setVisible(true);
    }

    /**
     * @return the object of the Board class
     */
    public Board getBoard() { return board; }

    /**
     * Sets the score text to display.
     * @param sc the current score of the player
     */
    public static void setScoreText(int sc) { Window.score.setText(String.valueOf(sc)); }

    /**
     * Enables/disables the button which starts the game.
     * @param bl value determining if enable or disable the button (true- enable; false- disable)
     */
    public static void enableStart(boolean bl) { start.setEnabled(bl); }

    /**
     * Enables/disables the button which displays highscores.
     * @param bl value determining if enable or disable the button (true- enable; false- disable)
     */
    public static void enableHighcores(boolean bl) { highscores.setEnabled(bl); }

    /**
     * Inner class for listening the events for the button which starts the game
     */
    private class StartListener implements  ActionListener
    {
        /**
         * After clicking on the button which starts the game disables the buttons start and highscores, changes the
         * displayed score and starts the timer.
         * @param e ActionEvent class' object
         */
        public void actionPerformed(ActionEvent e){
            start.setEnabled(false);
            highscores.setEnabled(false);
            controller.orderToResetScore();
            setScoreText(controller.readScore());
            controller.startTimer();
        }
    }

    /**
     * Inner class for listening the events for the button which displays highscores.
     */
    private static class ScoreListener implements ActionListener
    {
        /**
         * After clicking on the button which displays highscores makes the new object of class ScoresWindow.
         * @param e ActionEvent class' object
         */
        public void actionPerformed(ActionEvent e)
        {
            new ScoresWindow();
        }
    }

    /**
     * Inner class for listening the events for the button which ends the game.
     */
    private class EndGameListener implements ActionListener
    {
        /**
         * After clicking on the button which ends the game tops the timer and calls the JFrame's dispose() method.
         * @param e ActionEvent class' object
         */
        public void actionPerformed(ActionEvent e)
        {
            controller.stopTimer();
            dispose();
        }
    }
}
