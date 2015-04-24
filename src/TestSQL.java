import org.omg.Dynamic.Parameter;

import java.sql.*;

public class TestSQL {
    public static void main(String[] args){
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String temp = "jdbc:sqlserver://tg33.mysql.cs.st-andrews.ac.uk:3306/tg33_db?user=tg33&password=cyQaQ3x.";
            String url = "jdbc:mysql://tg33.mysql.cs.st-andrews.ac.uk/tg33_db";
            String dbName = "tg33_db";
            String user = "tg33";
            String pass = "cyQaQ3x.";
            con = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = con.prepareStatement("select * from " + "Artist" + " where gender = ?");
            ps.setString(1, "F");
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd.getColumnName(2));
            while (rs.next()){
                for (int i = 1; i <= rsmd.getColumnCount(); i++){
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }







        } catch (Exception e) {
            System.err.println("Error in creating the connection");
            e.printStackTrace();
        }



    }
}
