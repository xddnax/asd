import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import sun.font.GraphicComponent;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Represents a console viewable through a <code>JTextArea</code>.
 * 
 * <p>
 * Implementation: <code>
 *      System.setOut(new PrintStream(new Console( ... )));
 *  </code>
 * </p>
 * 
 * @author Derive McNeill
 *
 */
class Console extends OutputStream {

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
	public Console(JTextArea output) {
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

public class MainClass {
	public static Connection getDBconn() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		String temp = "jdbc:sqlserver://tg33.mysql.cs.st-andrews.ac.uk:3306/tg33_db?user=tg33&password=cyQaQ3x.";
		String url = "jdbc:mysql://tg33.mysql.cs.st-andrews.ac.uk/tg33_db";
		String dbName = "tg33_db";
		String driver = "com.mysql.ojdbc.Driver";
		String user = "tg33";
		String pass = "cyQaQ3x.";
		try {

			return DriverManager.getConnection(url, user, pass);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws SQLException,
			ClassNotFoundException, InterruptedException {

		Connection conn = getDBconn();
		if (conn == null) {
			System.exit(1);
		}

		
		GUI g = new GUI();
		g.setGui();
		JFrame frame = g.getGui();
		String SQL = "select * from Artist where gender = ?";
		SelectStatementFromSQLString(SQL, "M", 1, conn);
	}

	private static void SelectStatementFromSQLString(String SQL,
			String whatToSelect, int args, Connection conn) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(SQL);
		pstmt.setString(args, whatToSelect);
		ResultSet rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		while (rs.next()) {
			for (int i = 0; i < columns; i++) {
				System.out.print(rs.getString(i + 1) + "\t");
			}
			System.out.print("\n");
		}
	}
}
class GUI extends JFrame{
JFrame frame;
	public GUI(){
 }
public void setGui(){
	  frame = new JFrame("Database GUI");
	  Dimension d = new Dimension(1000, 1000);
		frame.setPreferredSize(d);
		frame.setVisible(true);

		JPanel uPanel = new JPanel();
		JTextArea ta = new JTextArea();
		JButton selectButton = new JButton("select smth");
		JTextField textInput1 = new JTextField("enter what to select here");
		textInput1.setBounds(0, 0, 50, 50);
		
		selectButton.setBounds(100, 100, 150, 50);;
		System.setOut(new PrintStream(new Console(ta)));
		ta.setLineWrap(false);
		ta.setBounds(220, 100, 300, 400);
		//uPanel.add(ta);
		uPanel.add(textInput1);
		uPanel.add(selectButton);
		
		
		
		JButton insertButton = new JButton("insert smth");
		JTextField textInput2=new JTextField("enter what to select here");
//		textInput2.setBounds(0, 0, 50, 50);
//		
//		insertButton.setBounds(100, 100, 150, 50);;
		JPanel mPanel = new JPanel();
		mPanel.add(insertButton);
		mPanel.add(textInput2);
		frame.add(ta);
		frame.getContentPane().add(mPanel);
		frame.getContentPane().add(uPanel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
 }
public JFrame getGui(){
	return frame;
}
}
