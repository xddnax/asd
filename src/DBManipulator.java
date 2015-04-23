import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class DBManipulator {
    private ArrayList<String> tables;
    private Connection con;
    private DBTable dbTable;
    private PreparedStatement pstmt;
    private String SQL;
    private ResultSet rs;
    private ResultSetMetaData rsmd;

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
            System.err.println("Error in creating the connection");
            e.printStackTrace();
		}
	}
	
	public void terminateConnection(){
		try {
			con.close();
		} catch (Exception e) {
            System.err.println("Error in closing the connection");
            e.printStackTrace();
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
        SQL = "Show tables";
        try {
            pstmt = con.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            while (rs.next()){
                this.tables.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching tables");
            e.printStackTrace();
        }
    }

    public void chooseTable(String table){
        ArrayList<String> columnNames = new ArrayList<String>();
        ArrayList<String> columnData = new ArrayList<String>();

        SQL = "Select * from " + table;
        try {
            pstmt = con.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            rsmd = rs.getMetaData();

            // Get table column count and column names
            int colNum = rsmd.getColumnCount();
            for (int i = 1; i<=colNum; i++){
                columnNames.add(rsmd.getColumnName(i));
            }
            dbTable = new DBTable(table, columnNames);
            while (rs.next()){
                columnData = new ArrayList<String>();
                for (int i = 1; i<=colNum; i++){
                    columnData.add(rsmd.getColumnName(i));
                }
                dbTable.addRecord(new DBRecord(columnData, "1"));
            }
        } catch (SQLException e) {
            System.err.println("Error in choosing table: " + table);
            System.err.println("SQL: " + SQL);
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTables() {
        return tables;
    }

    public DBTable getDbTable() {
        return dbTable;
    }
}
