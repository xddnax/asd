import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;


public class Test {

	public static void main(String[] args) throws SQLException {
		Queries qs = new Queries();
		qs.openDBconn();
		ResultSet rs;
		ResultSetMetaData rsmd;
		rs = qs.getSelectQuery("de", new HashMap<>()).executeQuery();
		rsmd = rs.getMetaData();
		while(rs.next()){
			System.out.println(rs.getString(1));
		}
	}

}
