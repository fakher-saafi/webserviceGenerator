import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**  *  * Java program to connect to MySQL Server database running on localhost,  * using JDBC type 4 driver.  *  * @author http://java67.blogspot.com  */
public class jdbc {
    public static void main(String args[]) {
        Connection con = null;
        try {
            String url = "jdbc:mysql://localhost:3306/app3?useUnicode=true&characterEncoding=utf8&useSSL=false";
            String username = "root";
            String password = "";
            // Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            if (con != null) { System.out .println("Successfully connected to MySQL database test"); }
        } catch (SQLException ex) {
            System.out .println("An error occurred while connecting MySQL databse"); ex.printStackTrace();
        }
    }
}
