import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Test {

	public static void main(String[] args) throws SQLException {
<<<<<<< HEAD
		Queries qs = new Queries();
		qs.openDBconn();
		ResultSet rs;
		ResultSetMetaData rsmd;
		rs = qs.getSelectQuery("de", new HashMap<>()).executeQuery();
		rsmd = rs.getMetaData();
		while(rs.next()){
			System.out.println(rs.getString(1));
		}
=======
		DBManipulator qs = new DBManipulator();
        ArrayList<String> as = qs.getTables();
        for (int i = 0; i < as.size(); i++){
            System.out.println(as.get(i));
        }
>>>>>>> e7884641acd1d766f2f152192f62dcd074b1a12b
	}

}
