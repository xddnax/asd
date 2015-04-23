import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.*;

public class FirstWindow extends JFrame implements ActionListener{
    ArrayList<String> tableNames = new ArrayList<String>();
    JPanel panel;

    public FirstWindow(){
        super("Add component on DBManipulator at runtime");
        tableNames.add("name 1");
        tableNames.add("bbt");
        tableNames.add("bato");

        setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        add(panel, BorderLayout.CENTER);
        JButton button = new JButton("Get tables");
        add(button, BorderLayout.SOUTH);
        button.addActionListener(this);
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        for(int i=0;i<tableNames.size();i++){
            panel.add(new JButton(tableNames.get(i)));
            panel.revalidate();
            validate();
        }
//        JPanel panel2 = new JPanel();
//        JTextArea ta = new JTextArea();
//        JButton selectButton = new JButton("select smth");
//        JTextField textInput1 = new JTextField("enter what to select here");
//        panel2.add(ta);
//        panel2.add(selectButton);
//        panel2.add(textInput1);
//        panel2.setVisible(true);

    }
    public static void main(String[] args) {
        FirstWindow fw = new FirstWindow();

    }
}
