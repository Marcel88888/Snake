import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Point;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Font;

public class ScoresWindow extends JDialog
{
    private JTextArea textArea;
    private String text = "";
    private JButton ok;

    private final String HIGHSCORES_FILE_PATH = "/home/dell/Studia/sem4/PROZ/Snake/src/highscores.txt";

    private final int WIDTH = 280;
    private final int HEIGHT = 190;

    public ScoresWindow()
    {
        setTitle("Highscores");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setModal(true);
        setLocation(new Point(310, 200));

        textArea = new JTextArea();
        textArea.setBounds(70, 15, 160, 90);
        textArea.setEditable(false);
        textArea.setBackground(new Color(238, 238, 238));
        textArea.setFont(new Font("Verdana", Font.BOLD, 12));

        ok = new JButton("OK");
        ok.setBounds(115, 110, 50, 30);
        ok.setFocusable(false);
        ok.setMargin(new Insets(1, 1, 1, 1));
        ok.addActionListener(new Close());

        displayHighscores();

        add(textArea);
        add(ok);

        setVisible(true);
    }

    public void displayHighscores()
    {
        try
        {
            FileReader reader = new FileReader(HIGHSCORES_FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String tmp = "";
            int i = 1;

            while ((tmp = bufferedReader.readLine()) != null)
            {
                StringTokenizer tokenizer = new StringTokenizer(tmp);
                text += String.valueOf(i) + ". ";
                while (tokenizer.hasMoreTokens())
                {
                    String temp = tokenizer.nextToken();
                    if (temp.equals("|"))
                        text += " -  " + tokenizer.nextToken() + "\n";
                    else
                        text += temp + "";
                }
                ++i;
            }
            bufferedReader.close();
        }
        catch(IOException exc) { System.out.println(exc.getMessage()); }
        textArea.setText(text);
    }

    private class Close implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            dispose();
        }
    }
}
