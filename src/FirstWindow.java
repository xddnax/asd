import java.awt.*;	
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.*;
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
    
    public FirstWindow(){
        super("Add component on DBManipulator at runtime");
        tableNames= dbm.getTables();
       
        setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        add(panel, BorderLayout.CENTER);
        JButton button = new JButton("Get tables");
        add(button, BorderLayout.SOUTH);
        panel2= new JPanel();
        add(panel2,BorderLayout.NORTH);
        panel2.setVisible(false);
        button.addActionListener(this);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        for(int i=0;i<tableNames.size();i++){
        	JButton button = new JButton(tableNames.get(i));
        	button.setName(tableNames.get(i));
        	panel.add(button);
            button.addActionListener(new ActionListener(){
            	public void actionPerformed(ActionEvent e){
            		JTextArea ta = new JTextArea();
            		panel2.add(ta);
            		panel.setVisible(false);
            		panel2.setVisible(true);
            		 System.setOut(new PrintStream(new ConsoleX(ta)));
            	        ta.setLineWrap(false);
            	       dbm.chooseTable(button.getName());
            	        System.out.println(dbm.getDbTable().getColumns().get(0));
            	}
            });
            panel.revalidate();
            validate();
        }
        }

    public void selectedTable(ActionEvent evt){
    	
    }
    public static void main(String[] args) {
        FirstWindow fw = new FirstWindow();

    }
}
