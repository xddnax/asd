import java.awt.*;	
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import com.sun.org.apache.xpath.internal.operations.Minus;
class ConsoleX extends OutputStream {

    /**
     * Represents the data written to the stream.
     */
    ArrayList<Byte> data = new ArrayList<Byte>();

    /**
     * Represents the text area that will be showing the written data.
     */
    private JTextArea output;

    /**
     * Creates a console context.
     *
     * @param output
     *            The text area to output the consoles text.
     */
    public ConsoleX(JTextArea output) {
        this.output = output;
    }

    /**
     * Called when data has been written to the console.
     */
    private void fireDataWritten() {

        // First we loop through our written data counting the lines.
        int lines = 0;
        for (int i = 0; i < data.size(); i++) {
            byte b = data.get(i);

            // Specifically we look for 10 which represents "\n".
            if (b == 10) {
                lines++;
            }

            // If the line count exceeds 250 we remove older lines.
            if (lines >= 250) {
                data = (ArrayList<Byte>) data.subList(i, data.size());
            }
        }

        // We then create a string builder to append our text data.
        StringBuilder bldr = new StringBuilder();

        // We loop through the text data appending it to the string builder.
        for (byte b : data) {
            bldr.append((char) b);
        }

        // Finally we set the outputs text to our built string.
        output.setText(bldr.toString());
    }

    @Override
    public void write(int i) throws IOException {

        // Append the piece of data to our array of data.
        data.add((byte) i);

        // Indicate that data has just been written.
        fireDataWritten();
    }

}

public class FirstWindow extends JFrame implements ActionListener{
	private DBManipulator dbm = new DBManipulator();
	
    ArrayList<String> tableNames = new ArrayList<String>();
    
    JPanel panel;
    JPanel panel2;
   
    HashMap<String, String> names = new HashMap<String,String>();
    HashMap<String, String> insertQuery = new HashMap<String,String>();
    ArrayList<String> insertNamesList = new ArrayList<String>();
    ArrayList<String> insertValuesList=new ArrayList<String>();
    ArrayList<JTextField> textsList = new ArrayList<JTextField>();
    HashMap<JButton, ArrayList<String>> buttonMap=new HashMap<JButton, ArrayList<String>>();
    public FirstWindow(){
        super("Add component on DBManipulator at runtime");
        tableNames= dbm.getTables();
       
        setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        add(panel, BorderLayout.CENTER);
        panel2= new JPanel();
       // add(panel2,BorderLayout.NORTH);
       // panel2.setVisible(false);
        for(int i=0;i<tableNames.size();i++){
        	JButton button = new JButton(tableNames.get(i));

        	names.put(button.getName(), tableNames.get(i));
        	panel.add(button);
            button.addActionListener(this);
            panel.revalidate();
            validate();
        }
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setVisible(true);
    }

	public void actionPerformed(ActionEvent e){
		System.out.println(e.getActionCommand());
//		JTextArea ta = new JTextArea();
//		panel2.add(ta);
		panel.setVisible(false);
		panel2.setVisible(true);
		panel2.setLayout(null);
		//panel2.setBackground(Color.WHITE);
		add(panel2);
		panel2.add(new JScrollPane());
//		 System.setOut(new PrintStream(new ConsoleX(ta)));
//	        ta.setLineWrap(false);
		Insets insets = panel2.getInsets();
	        dbm.chooseTable(e.getActionCommand());
	        int xPos =100;
	        int yPos =50;
	        for(String c : dbm.getDbTable().getColumns()){
	        	 JLabel label = new JLabel(c);
	        	 insertNamesList.add(c);
	        	 Dimension size = label.getPreferredSize();
	        	 label.setBounds(insets.left+xPos, insets.top+5, size.width, size.height);
	        	
	        	 xPos+=150;
	        	 panel2.add(label);
	        }
	        xPos=100;
	        yPos+= 50;
//	        }
	        int count=0;
	        
	        for(DBRecord r : dbm.getDbTable().getRecords()){
	        	ArrayList<String> temp = new ArrayList<String>();
	        	xPos=100;
	        	count=0;
	        	JButton minusButton=new JButton("-");
	        	Dimension buttSize = minusButton.getPreferredSize();
	        	minusButton.setBounds(15, yPos, buttSize.width, buttSize.height);
	        	panel2.add(minusButton);
	        	minusButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent f) {
						dbm.deleteRow(buttonMap.get((JButton)f.getSource()));
						minusButton.setVisible(false);
						update();
					}
				});
	        	for(String s : r.getColumnValues()){
	        		JTextArea label = new JTextArea(s);
	        		label.setBackground(Color.WHITE);
	        		Dimension size = label.getPreferredSize();
	        		temp.add(s);
	        		label.setBounds(insets.left + xPos, insets.top+yPos, size.width, size.height);
	        		label.setVisible(true);
	        		xPos+=150;
	        		//System.out.println(s);
		        	panel2.add(label);
		        	panel2.revalidate();
		        	validate();
		        	count++;
	        	}
	        	yPos+=50;
	        	
	        	buttonMap.put(minusButton, temp);
	        	//temp.clear();
	        }
	        xPos=0;
	        JButton plusButton=new JButton("+");
        	Dimension buttSize = plusButton.getPreferredSize();
        	plusButton.setBounds(xPos, yPos, buttSize.width, buttSize.height);
        	panel2.add(plusButton);
        	plusButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					insertValuesList.clear();
					for(int i=0;i<textsList.size();i++){
					insertValuesList.add(textsList.get(i).getText());	
					//	System.out.println(textsList.get(i).getText() + "    ");
					}
					dbm.insertRow(insertValuesList);
				
					update();
					plusButton.setVisible(false);
			
				}
			});
        	xPos+=50;
        	for(int i=0;i<count;i++){
        		JTextField jtf = new JTextField("input Here");
             	Dimension textSize = jtf.getPreferredSize();
            	jtf.setBounds(xPos, yPos, textSize.width, textSize.height);
            	panel2.add(jtf);
            	textsList.add(jtf);
            	xPos+=150;
            	
        	}
     		xPos=75;
        	for(int i=0;i<count;i++){
       
        		JTextField jtf = new JTextField("input Filter");
             	Dimension textSize = jtf.getPreferredSize();
            	jtf.setBounds(xPos, 50, textSize.width, textSize.height);
            	panel2.add(jtf);
            	xPos+=150;
        	}
        	
        	JButton backButton = new JButton("go back");
        	Dimension bbSize = backButton.getPreferredSize();
        	backButton.setBounds(0, 0, bbSize.width, bbSize.height);
        	//backButton.addActionListener();
        	panel2.add(backButton);
        	backButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					panel2.setVisible(false);
					panel.setVisible(true);
					panel2=new JPanel();
				}
			});
	        validate();
	}
	
	private void update(){
		//System.out.println(e.getActionCommand());
//		JTextArea ta = new JTextArea();
//		panel2.add(ta);
		 tableNames= dbm.getTables();
	       panel2.removeAll();
		
		panel.setVisible(false);
		panel2.setVisible(true);
		panel2.setLayout(null);
		//panel2.setBackground(Color.WHITE);
		add(panel2);
		panel2.add(new JScrollPane());
//		 System.setOut(new PrintStream(new ConsoleX(ta)));
//	        ta.setLineWrap(false);
		Insets insets = panel2.getInsets();
	        dbm.chooseTable(dbm.getDbTable().getTableName());
	        int xPos =100;
	        int yPos =50;
	        for(String c : dbm.getDbTable().getColumns()){
	        	 JLabel label = new JLabel(c);
	        	 insertNamesList.add(c);
	        	 Dimension size = label.getPreferredSize();
	        	 label.setBounds(insets.left+xPos, insets.top+5, size.width, size.height);
	        	
	        	 xPos+=150;
	        	 panel2.add(label);
	        }
	        xPos=100;
	        yPos+= 50;
//	        }
	        int count=0;
	        for(DBRecord r : dbm.getDbTable().getRecords()){
	        	ArrayList<String> temp = new ArrayList<String>();
	        	xPos=100;
	        	count=0;
	        	JButton minusButton=new JButton("-");
	        	Dimension buttSize = minusButton.getPreferredSize();
	        	minusButton.setBounds(15, yPos, buttSize.width, buttSize.height);
	        	panel2.add(minusButton);
	        	minusButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent f) {
						dbm.deleteRow(buttonMap.get((JButton)f.getSource()));
						minusButton.setVisible(false);
						update();
					}
				});
	        	for(String s : r.getColumnValues()){
	        		JTextArea label = new JTextArea(s);
	        		label.setBackground(Color.WHITE);
	        		Dimension size = label.getPreferredSize();
	        		temp.add(s);
	        		label.setBounds(insets.left + xPos, insets.top+yPos, size.width, size.height);
	        		label.setVisible(true);
	        		xPos+=150;
	        		//System.out.println(s);
		        	panel2.add(label);
		        	panel2.revalidate();
		        	validate();
		        	count++;
	        	}
	        	yPos+=50;
	        	buttonMap.put(minusButton, temp);
	        	temp.clear();
	        }
	        xPos=0;
	        JButton plusButton=new JButton("+");
        	Dimension buttSize = plusButton.getPreferredSize();
        	plusButton.setBounds(xPos, yPos, buttSize.width, buttSize.height);
        	panel2.add(plusButton);
        	plusButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					insertValuesList.clear();
					for(int i=0;i<textsList.size();i++){
					insertValuesList.add(textsList.get(i).getText());	
					//	System.out.println(textsList.get(i).getText() + "    ");
					}
					dbm.insertRow(insertValuesList);
				
					update();
					plusButton.setVisible(false);
			
				}
			});
        	xPos+=50;
        	for(int i=0;i<count;i++){
        		JTextField jtf = new JTextField("input Here");
             	Dimension textSize = jtf.getPreferredSize();
            	jtf.setBounds(xPos, yPos, textSize.width, textSize.height);
            	panel2.add(jtf);
            	textsList.add(jtf);
            	xPos+=150;
            	
        	}
     		xPos=75;
        	for(int i=0;i<count;i++){
       
        		JTextField jtf = new JTextField("input Filter");
             	Dimension textSize = jtf.getPreferredSize();
            	jtf.setBounds(xPos, 50, textSize.width, textSize.height);
            	panel2.add(jtf);
            	xPos+=150;
        	}
        	
        	JButton backButton = new JButton("go back");
        	Dimension bbSize = backButton.getPreferredSize();
        	backButton.setBounds(0, 0, bbSize.width, bbSize.height);
        	//backButton.addActionListener();
        	panel2.add(backButton);
        	backButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					panel2.setVisible(false);
					panel.setVisible(true);
					panel2=new JPanel();
				}
			});
	        validate();
	}

    
    public static void main(String[] args) {
        FirstWindow fw = new FirstWindow();
      

    }
}
