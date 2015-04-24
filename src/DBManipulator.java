import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class DBManipulator {
    private ArrayList<String> tables;
    private Connection con;
    private DBTable dbTable;
    private PreparedStatement pstmt;
    private Statement stmt;
    private String SQL;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private DatabaseMetaData dmd;

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
            dmd = con.getMetaData();
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

        SQL = "Select * from `" + table + "`";
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

            rs = dmd.getPrimaryKeys(null, null, table);
            rs.next();
            dbTable.setPK(rs.getString(4));

        } catch (SQLException e) {
            System.err.println("Error in choosing table:"+table+"!");
            System.err.println("SQL: " + SQL + "!");
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTables() {
        return tables;
    }

    public DBTable getDbTable() {
        return dbTable;
    }
    
    public String deleteRow(ArrayList<String> values){
        String pkCol = dbTable.getColumns().get(dbTable.getPK());
        System.out.println(values.toString());
        String pkVal = values.get(dbTable.getPK());



        SQL = "delete from `" + dbTable.getTableName() + "` where " + pkCol + " = '" + pkVal + "' ";
        try {
            stmt = con.createStatement();
            //System.err.println("SQL: " + SQL);

            stmt.executeUpdate(SQL);

        } catch (SQLException e) {
            System.err.println("Error in deleting row from " + dbTable.getTableName());
            System.err.println("SQL: " + SQL);
            e.printStackTrace();
            return "Error in deleting row from " + dbTable.getTableName();
        }
        return "query ok";
    }

    public String insertRow(ArrayList<String> values){
        int result;
        SQL ="";
        SQL+="insert into " + dbTable.getTableName() + " ( ";
        for (int i = 0; i < dbTable.getColumns().size(); i++){
            if (i == dbTable.getColumns().size()-1){
                SQL+=dbTable.getColumns().get(i) + " ) ";
            } else {
                SQL+=dbTable.getColumns().get(i) + " , ";
            }
        }
        SQL+=" values ( ";
        for (int i = 0; i < values.size(); i++){
            if (i == values.size()-1){
                SQL+="'" + values.get(i) + "' ) ";
            } else {
                SQL+="'" + values.get(i) + "' , ";
            }
        }
        try {
            stmt = con.createStatement();
            result = stmt.executeUpdate(SQL);
        } catch (SQLException e) {
            System.err.println("Error in inserting row into " + dbTable.getTableName());
            System.err.println("SQL: " + SQL);
            e.printStackTrace();
            return "Error in inserting row " + dbTable.getTableName();
        }
        return "query ok";
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

    public DatabaseMetaData getDmd() {
        return dmd;
    }
}
