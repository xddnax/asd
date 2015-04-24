import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Test {

	public static void main(String[] args) throws SQLException {
		DBManipulator qs = new DBManipulator();
        ArrayList<String> as = qs.getTables();
        for (int i = 0; i < as.size(); i++){
            System.out.println(as.get(i));
        }
        qs.chooseTable("Artist");
        DBTable dbt = qs.getDbTable();
        for (String s : dbt.getColumns()){
            System.out.println(s);
        }

        for (DBRecord dbr : dbt.getRecords()){
            System.out.println(dbr);
        }
        ArrayList<String> vals = new ArrayList<String>();
        vals.add("asd");
        vals.add("M");

//        qs.deleteRow(vals);

        DatabaseMetaData dmd = qs.getDmd();
        ResultSet rs = dmd.getPrimaryKeys(null, null, "Storage_Box");
        ResultSetMetaData rsmd = rs.getMetaData();
        while(rs.next()){
            for (int i = 1; i <= rsmd.getColumnCount(); i++){
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }

        rs = dmd.getPrimaryKeys(null, null, "Album");
        rsmd = rs.getMetaData();
        while(rs.next()){
            for (int i = 1; i <= rsmd.getColumnCount(); i++){
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }


        rs = dmd.getPrimaryKeys(null, null, "Record");
        rsmd = rs.getMetaData();
        while(rs.next()){
            for (int i = 1; i <= rsmd.getColumnCount(); i++){
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }
	}

}
