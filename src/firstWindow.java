import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class firstWindow extends JFrame implements ActionListener{
	ArrayList<String> tableNames = new ArrayList<String>();
	    JPanel panel;
	    
	    public firstWindow(){
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
	    
	    }
	    public static void main(String[] args) {
			firstWindow fw = new firstWindow();
			
		}
}
