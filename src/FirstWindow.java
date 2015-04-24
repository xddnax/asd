import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;


public class FirstWindow extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DBManipulator dbm = new DBManipulator();

    ArrayList<String> tableNames = new ArrayList<String>();

    JPanel panel;
    JPanel panel2;

    HashMap<String, String> names = new HashMap<String, String>();
    HashMap<String, String> insertQuery = new HashMap<String, String>();
    ArrayList<String> insertNamesList = new ArrayList<String>();
    ArrayList<String> insertValuesList = new ArrayList<String>();
    ArrayList<JTextField> textsList = new ArrayList<JTextField>();
    HashMap<JButton, ArrayList<String>> buttonMap = new HashMap<JButton, ArrayList<String>>();

    public FirstWindow() {
        super("DBManipulator");
        tableNames = dbm.getTables();
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setVisible(true);
        this.createPanel1();
        
    }

    private void createPanel1() {
    	
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(1000, 1000));
        panel.setBackground(Color.RED);
        panel.setVisible(true);
        add(panel);
        
        for (int i = 0; i < tableNames.size(); i++) {
            JButton button = new JButton(tableNames.get(i));
            names.put(button.getName(), tableNames.get(i));
            panel.add(button);
            button.addActionListener(this);
            panel.revalidate();
            validate();
        }
       
    }

    private void createPanel2() {
    	panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(1000, 1000));
        panel2.setBackground(Color.RED);
        panel2.setVisible(true);
        add(panel2);
    }

    private void initPanel2(String table){
        panel.setVisible(false);
      //  panel.removeAll();
        panel2.setVisible(true);
        panel2.setLayout(null);
        Insets insets = panel2.getInsets();
        dbm.chooseTable(table);
        int xPos = 100;
        int yPos = 50;
        for (String c : dbm.getDbTable().getColumns()) {
            JLabel label = new JLabel(c);
            insertNamesList.add(c);
            Dimension size = label.getPreferredSize();
            label.setBounds(insets.left + xPos, insets.top + 5, size.width, size.height);

            xPos += 200;
            panel2.add(label);
        }
        xPos = 100;
        yPos += 50;
        int count = 0;
    	int unChangeable = dbm.getDbTable().getPK();
        for (DBRecord r : dbm.getDbTable().getRecords()) {

            ArrayList<String> temp = new ArrayList<String>();
            xPos = 100;
            count = 0;
            JButton minusButton = new JButton("-");
            Dimension buttSize = minusButton.getPreferredSize();
            minusButton.setBounds(15, yPos, buttSize.width, buttSize.height);
            panel2.add(minusButton);
            minusButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent f) {
                    dbm.deleteRow(buttonMap.get((JButton) f.getSource()));
//						minusButton.setVisible(false);
               //     createPanel1();
                    dbm.chooseTable(table);
                    initPanel2(table);
                    
                }
            });
            for (String s : r.getColumnValues()) {
            	if(count == unChangeable){
            		 JLabel label = new JLabel(s);
                     label.setBackground(Color.WHITE);
                     Dimension size = label.getPreferredSize();
                     temp.add(s);
                     label.setBounds(insets.left + xPos, insets.top + yPos, size.width, size.height);
                     label.setVisible(true);
                     
                     xPos += 150;
                     
                     
                     panel2.add(label);
            	}
            	else{
                JTextArea label = new JTextArea(s);
                label.setBackground(Color.WHITE);
                Dimension size = label.getPreferredSize();
                temp.add(s);
                label.setBounds(insets.left + xPos, insets.top + yPos, size.width, size.height);
                label.setVisible(true);
                
                xPos += 150;
                
                
                panel2.add(label);
            	}
                
                panel2.revalidate();
                validate();
                count++;
            }
            JButton updateBut = new JButton("U");
            Dimension si = updateBut.getPreferredSize();
            updateBut.setBounds(insets.left+xPos, insets.top+yPos, si.width, si.height);
            updateBut.setVisible(true);
            xPos+=50;
            panel2.add(updateBut);
            yPos += 50;

            buttonMap.put(minusButton, temp);
            //temp.clear();
        }
        xPos = 0;
        JButton plusButton = new JButton("+");
        Dimension buttSize = plusButton.getPreferredSize();
        plusButton.setBounds(xPos, yPos, buttSize.width, buttSize.height);
        panel2.add(plusButton);
        plusButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                insertValuesList.clear();
                for (int i = 0; i < textsList.size(); i++) {
                    insertValuesList.add(textsList.get(i).getText());
                    //	System.out.println(textsList.get(i).getText() + "    ");
                }
                dbm.insertRow(insertValuesList);
                initPanel2(dbm.getDbTable().getTableName());
            }
        });
        xPos += 50;
        for (int i = 0; i < count; i++) {
            JTextField jtf = new JTextField("input Here");
            Dimension textSize = jtf.getPreferredSize();
            jtf.setBounds(xPos, yPos, textSize.width, textSize.height);
            panel2.add(jtf);
            textsList.add(jtf);
            xPos += 200;

        }
        xPos = 75;
        for (int i = 0; i < count; i++) {

            JTextField jtf = new JTextField("input Filter");
            Dimension textSize = jtf.getPreferredSize();
            jtf.setBounds(xPos, 50, textSize.width, textSize.height);
            panel2.add(jtf);
            xPos += 150;
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
                panel2 = new JPanel();
            }
        });
        validate();
    }

    public void actionPerformed(ActionEvent e) {
    	
    	createPanel2();
    	initPanel2(e.getActionCommand());
    }


    public static void main(String[] args) {
        FirstWindow fw = new FirstWindow();
    }
}
