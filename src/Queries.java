import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Queries {
	private Connection con;
	
	public Queries(){
		super();
	}
	
	public void openDBconn(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String temp = "jdbc:sqlserver://tg33.mysql.cs.st-andrews.ac.uk:3306/tg33_db?user=tg33&password=cyQaQ3x.";
			String url = "jdbc:mysql://tg33.mysql.cs.st-andrews.ac.uk/tg33_db";
			String dbName = "tg33_db";
			String user = "tg33";
			String pass = "cyQaQ3x.";
			con = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void terminateConnection(){
		try {
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public PreparedStatement getSelectQuery(String table, HashMap<String, String> params){
		PreparedStatement select = null;
		try {
			select = con.prepareStatement("Select * from Artist");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return select;
	}
}
