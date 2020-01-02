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
import java.awt.Font;

public class ScoresWindow extends JDialog
{
    private JTextArea textArea;
    private String text = "";
    StringBuilder sb = new StringBuilder();

    public ScoresWindow()
    {
        setTitle("Highscores");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        int width = 280;
        int height = 190;
        setSize(width, height);
        setResizable(false);
        setModal(true);
        setLocation(new Point(770, 400));

        textArea = new JTextArea();
        textArea.setBounds(70, 15, 160, 90);
        textArea.setEditable(false);
        textArea.setBackground(new Color(238, 238, 238));
        textArea.setFont(new Font("Helvetica", Font.BOLD, 12));

        JButton ok = new JButton("OK");
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
            String highscores_file_path = "/home/dell/Studia/sem4/PROZ/Snake/src/highscores.txt";
            FileReader reader = new FileReader(highscores_file_path);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String tmp;
            int i = 1;

            while ((tmp = bufferedReader.readLine()) != null)
            {
                StringTokenizer tokenizer = new StringTokenizer(tmp);
                text = sb.append(i).append(". ").toString();
                while (tokenizer.hasMoreTokens())
                {
                    String temp = tokenizer.nextToken();
                    if (temp.equals("|"))
                        text = sb.append(" -  ").append(tokenizer.nextToken()).append("\n").toString();
                    else
                        text = sb.append(temp).toString();
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
