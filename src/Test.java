import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class Test {

	public static void main(String[] args) throws SQLException {
		Queries qs = new Queries();
		qs.openDBconn();
		ResultSet rs;
		rs = qs.getSelectQuery("de", new HashMap<>()).executeQuery();
		while(rs.next()){
			System.out.println(rs.getString(1));
		}
	}

}
