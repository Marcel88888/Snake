import javax.swing.*;
import java.awt.Point;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame
{
    private Controller controller;
    private Board board;

    /*private final int WIDTH = 380;`
    private final int HEIGHT = 350;TODO ZMIENIC WYMIAR*/

    private static JButton start;
    private static JButton scores;
    private static JButton endGame;
    private static JLabel txtScore;
    private static JLabel score;
    private static JLabel logo;

    public Window(Controller controller)
    {
        super("Snake");

        this.controller = controller;

        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocation(new Point(240, 120)); //??????????????????

        Insets insets = new Insets(1,1,1,1); //????????????????

        start = new JButton("Start");
        start.setMargin(insets);
        start.setFocusable(false);
        start.setBounds(407, 90, 80, 30);
        start.addActionListener(new StartListener());

        scores = new JButton("Scores");
        scores.setMargin(insets);
        scores.setFocusable(false);
        scores.setBounds(407, 130, 80, 30);
        //scores.addActionListener(new ScoreListener()); TODO

        endGame = new JButton("Exit");
        endGame.setMargin(insets);
        endGame.setFocusable(false);
        endGame.setBounds(407, 170, 80, 30);
        endGame.addActionListener(new EndGameListener());

        txtScore = new JLabel("Score:");
        txtScore.setBounds(406, 30, 45, 30);

        score = new JLabel("0");
        score.setBounds(460, 30, 42, 30);

        logo = new JLabel(new ImageIcon("snake.jpg"));
        logo.setBounds(401, 210, 92, 161);

        this.board = new Board(this.controller);
        board.setBounds(0, 0, board.getWidth(), board.getHeight());

        add(board);
        add(start);
        add(scores);
        add(txtScore);
        add(endGame);
        add(logo);
        add(score);

        setVisible(true);
    }

    public Board getBoard() { return board; }

    public static void setScoreText(int sc) {
        Window.score.setText(String.valueOf(sc));
    }

    public static void enableStart(boolean bl) {
        start.setEnabled(bl);
    }

    public static void enableScores(boolean bl) {
        scores.setEnabled(bl);
    }

    private class StartListener implements  ActionListener
    {
        public void actionPerformed(ActionEvent e){
            start.setEnabled(false);
            scores.setEnabled(false);
            controller.startTimer();
        }
    }

    /*private class ScoreListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //TODO
        }
    }*/

    private class EndGameListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            controller.stopTimer();
            dispose();
        }
    }
}
