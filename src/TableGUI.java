import java.awt.Dimension;	
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



    public JFrame getGui() {
        return frame;
    }
    public static void main(String[] args) {
		TableGUI tg = new TableGUI();
		DBManipulator dbm = new DBManipulator();
//    	tg.tabledata = dbm.getTables();
//		tg.setGui(tg.tabledata);
	}
}