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
	    this.openDBCon();
        this.fetchTables();
    }
	
	private void openDBCon(){
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
        ArrayList<String> columnData;

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
            this.dbTable = new DBTable(table, columnNames);
            while (rs.next()){
                columnData = new ArrayList<String>();
                for (int i = 1; i<=colNum; i++){
                    columnData.add(rs.getString(i));
                }
                this.dbTable.addRecord(new DBRecord(columnData, "1"));
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
    
    public void deleteRow(){
        SQL = "delete * from " + dbTable.getTableName() + " where ? = ?";
        try {
            pstmt = con.prepareStatement(SQL);
            rs = pstmt.executeQuery();

        } catch (SQLException e) {
            System.err.println("Error in deleting row " + dbTable.getTableName());
            System.err.println("SQL: " + SQL);
            e.printStackTrace();
        }
    }

    public void insertRow(ArrayList<String> values){
        StringBuilder sb = new StringBuilder();
        sb.append("insert into " + dbTable.getTableName());
        sb.append("( ");
        for (int i = 0; i < dbTable.getColumns().size(); i++){
            if (i == dbTable.getColumns().size()-1){
                sb.append(dbTable.getColumns().get(i) + ", ");
            } else {
                sb.append(dbTable.getColumns().get(i) + " ) ");
            }
        }
        sb.append(" values ( ");
        for (int i = 0; i < values.size(); i++){
            if (i == values.size()-1){
                sb.append(values.get(i) + ", ");
            } else {
                sb.append(values.get(i) + " ) ");
            }
        }
        SQL += sb.toString();
        try {
            pstmt = con.prepareStatement(SQL);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error in inserting row " + dbTable.getTableName());
            System.err.println("SQL: " + SQL);
            e.printStackTrace();
        }
    }

    public void filterResults(){
        SQL = "delete * from " + dbTable.getTableName() + " where ? = ?";
        try {
            pstmt = con.prepareStatement(SQL);
            rs = pstmt.executeQuery();

        } catch (SQLException e) {
            System.err.println("Error in deleting row " + dbTable.getTableName());
            System.err.println("SQL: " + SQL);
            e.printStackTrace();
        }
    }

    public void updateRow(){
        SQL = "delete * from " + dbTable.getTableName() + " where ? = ?";
        try {
            pstmt = con.prepareStatement(SQL);
            rs = pstmt.executeQuery();

        } catch (SQLException e) {
            System.err.println("Error in deleting row " + dbTable.getTableName());
            System.err.println("SQL: " + SQL);
            e.printStackTrace();
        }
    }
}
