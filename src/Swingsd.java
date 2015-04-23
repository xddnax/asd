import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Swingsd extends JFrame implements ActionListener {
    JPanel panel;

    public Swingsd() {
        super("Add component on JFrame at runtime");
        setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        add(panel, BorderLayout.CENTER);
        JButton button = new JButton("CLICK HERE");
        add(button, BorderLayout.SOUTH);
        button.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        panel.add(new JButton("Button"));
        panel.revalidate();
        validate();
    }
}

