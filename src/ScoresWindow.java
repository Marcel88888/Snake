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

/**
 * The class inheriting from JDialog class displaying the highscores
 */
public class ScoresWindow extends JDialog
{
    /**
     * The text area for displaying the usernames and scores.
     */
    private JTextArea textArea;
    /**
     * StringBuilder class' object for making a text to display from a text from the file with highscores.
     */
    StringBuilder sb = new StringBuilder();

    /**
     * The constructor of the class which sets the title of the window, the sizes of the elements of the window, the
     * font of the text, the colour of the backround, adds JButton object to close the window and all the necessary
     * things to display all the elements correctly.
     */
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

    /**
     * Builds a text to display from a text from the file and sets it.
     */
    public void displayHighscores()
    {
        String text = "";
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
        catch(IOException exc) { System.out.println(exc.getMessage());  }
        textArea.setText(text);
    }

    /**
     * Inner class for listening the events for the button which closes the ScoresWindow window.
     */
    private class Close implements ActionListener
    {
        /**
         * After clicking on the button which closes the ScoresWindow window calls the JFrame's dispose() method.
         * @param e ActionEvent class' object
         */
        public void actionPerformed(ActionEvent e) { dispose(); }
    }
}
