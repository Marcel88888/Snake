import javax.swing.JDialog;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.lang.*;

public class EndOfGame extends JDialog {

    private Controller controller;
    private JTextField name;

    public EndOfGame(Controller controller)
    {
        this.controller = controller;

        setTitle("Game Over");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        int width = 270;
        int height = 160;
        setSize(width, height);
        setModal(true);
        setLocation(new Point(770, 400));
        setResizable(false);

        JLabel nameTxt = new JLabel("Your nick:");
        nameTxt.setBounds(45, 20, 100, 20);
        nameTxt.setBackground(Color.white);

        name = new JTextField("");
        name.setBounds(125, 20, 120, 20);

        JLabel yourScore = new JLabel("Your score: " + controller.readScore());
        yourScore.setBounds(80, 50, 110, 20);
        yourScore.setBackground(Color.white);

        JButton ok = new JButton("OK");
        ok.setBounds(110, 85, 50, 30);
        ok.setFocusable(false);
        ok.setMargin(new Insets(1, 1, 1, 1));
        ok.addActionListener(new End());

        add(ok);
        add(nameTxt);
        add(name);
        add(yourScore);

        setVisible(true);
    }

    public void saveScore()
    {
        int currentScore = controller.readScore();
        HashMap<String, Integer> highscores = new HashMap<>();

        String highscores_file_path = "/home/dell/Studia/sem4/PROZ/Snake/src/highscores.txt";
        int highscores_size = 5;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(highscores_file_path));
            String tmp;
            while (highscores.size() < highscores_size && (tmp = reader.readLine()) != null)
            {
                String[] parts = tmp.split(" \\| ");
                String player = parts[0];
                String scoreStr = parts[1];
                int score = Integer.parseInt(scoreStr);
                highscores.put(player, score);
            }
            reader.close();
        }
        catch (IOException exc) { System.out.println(exc.getMessage());}

        if (highscores.isEmpty() && currentScore > 0)
        {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(highscores_file_path, false));
                writer.write(name.getText() + " | " + controller.readScore() + "\n");
                writer.close();
            }
            catch (IOException exc) { System.out.println(exc.getMessage()); }
        }

        HashMap<String, Integer> highscores2 = sortByValue(highscores);

        int i = 0;
        for (Map.Entry hs2:highscores2.entrySet()) {
            if (i == highscores2.size()-1)
            {
                Integer worstScoreValue = (Integer)hs2.getValue();
                String worstScoreKey = (String)hs2.getKey();
                if ((currentScore >= worstScoreValue || highscores2.size() < highscores_size) && currentScore > 0)
                {
                    if (highscores.size() >= highscores_size) { highscores2.remove(worstScoreKey); }
                    highscores2.put(name.getText(), controller.readScore());
                    HashMap<String, Integer> highscores3 = sortByValue(highscores2);
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(highscores_file_path, false));
                        for (Map.Entry hs3:highscores3.entrySet())
                        {
                            String key = (String)hs3.getKey();
                            Integer val = (Integer)hs3.getValue();
                            writer.write(key + " | " + val + "\n");
                        }
                        writer.close();
                    }
                    catch (IOException exc) { System.out.println(exc.getMessage()); }
                }
            }
            ++i;
        }
    }

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        List<Map.Entry<String, Integer> > list =
                new LinkedList<>(hm.entrySet());

        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private class End implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (!(name.getText().equals("")))
            {
                if (Character.isLetterOrDigit((name.getText()).charAt(0)))
                {
                    saveScore();
                    controller.orderToResetScore();
                    Window.setScoreText(0);
                    dispose();
                }
                else { name.setText("Unknown player");}
            }
        }
    }
}
