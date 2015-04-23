import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;


public class firstWindow extends JFrame{
	 ArrayList<String> tableNames=new ArrayList<String>();
	
	public firstWindow(ArrayList<String> tableNames){
		this.tableNames=tableNames;
	}
	public  JFrame getGUI(){
		int numOfTables = tableNames.size();
		JFrame frame = new JFrame();
		Dimension d = new Dimension(150*numOfTables + 100,100);
		frame.setPreferredSize(d);
		frame.setSize(d);
		for(int i=0;i<numOfTables;i++){
			JButton button = new JButton(tableNames.get(i));
			button.setBounds(50+150*i, 25, 150, 50);
		//	button.setPreferredSize(new Dimension(150,50));
		
			frame.add(button);
			
		}
		frame.setVisible(true);
	return frame;}
	
	
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("name 1");
		list.add("name 2");
		list.add("name 3");
		list.add("name 4");
		firstWindow fw = new firstWindow(list);
		JFrame frame = fw.getGUI();
		
		
	}
}
