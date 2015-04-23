import java.awt.Dimension;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TableGUI extends JFrame {
    ArrayList<String> tabledata;
    JFrame frame;

    public void setGui(ArrayList<ArrayList<String>> data) {
        frame = new JFrame("Database GUI");
        Dimension d = new Dimension(1000, 1000);
        frame.setPreferredSize(d);
        frame.setVisible(true);

        JTextArea ta = new JTextArea();
        JButton selectButton = new JButton("select smth");
        JTextField textInput1 = new JTextField("enter what to select here");
        textInput1.setBounds(0, 0, 50, 50);
        selectButton.setBounds(100, 100, 150, 50);
        ;
        System.setOut(new PrintStream(new Console(ta)));
        ta.setLineWrap(false);
        ta.setBounds(220, 100, 300, 400);
        frame.add(ta);
        ;
        int numOfTables = data.size();
        for (int i = 0; i < numOfTables; i++) {
            System.out.print(data.get(i).get(0) + "\t ");

        }
        for (int i = 0; i < numOfTables; i++) {
            for (int j = 1; j < data.get(i).size(); j++) {
                System.out.print(data.get(i).get(j) + " \n ");
            }
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

    public JFrame getGui() {
        return frame;
    }
}