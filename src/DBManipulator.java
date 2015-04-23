import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class DBManipulator {
    private ArrayList<String> tables;
    private Connection con;
	
	public DBManipulator(){
		super();
	    this.openDBconn();
        this.fetchTables();
    }
	
	private void openDBconn(){
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

    private void fetchTables(){
        this.tables = new ArrayList<String>();
        String SQL = "Show tables";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("Show tables");
            rs = pstmt.executeQuery();
            while (rs.next()){
                this.tables.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTables() {
        return tables;
    }
}
