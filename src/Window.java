import javax.swing.*;
import java.awt.Point;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame
{
    private Controller controller;
    private Board board;

    private static JButton start;
    private static JButton highscores;
    private static JLabel score;

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

    public Board getBoard() { return board; }

    public static void setScoreText(int sc) {
        Window.score.setText(String.valueOf(sc));
    }

    public static void enableStart(boolean bl) {
        start.setEnabled(bl);
    }

    public static void enableHighcores(boolean bl) {
        highscores.setEnabled(bl);
    }

    private class StartListener implements  ActionListener
    {
        public void actionPerformed(ActionEvent e){
            start.setEnabled(false);
            highscores.setEnabled(false);
            controller.orderToResetScore();
            setScoreText(0);
            controller.startTimer();
        }
    }

    private static class ScoreListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new ScoresWindow();
        }
    }

    private class EndGameListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            controller.stopTimer();
            dispose();
        }
    }
}
