import java.sql.ResultSet;
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
        qs.chooseTable("Album");
        DBTable dbt = qs.getDbTable();
        for (String s : dbt.getColumns()){
            System.out.println(s);
        }

        for (DBRecord dbr : dbt.getRecords()){
            System.out.println(dbr);
        }

	}

}
