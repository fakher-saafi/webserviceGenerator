import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.mycompany.myapp.service.mysqldbService;



/**  *  * Java program to connect to MySQL Server database running on localhost,  * using JDBC type 4 driver.  *  * @author http://java67.blogspot.com  */
public class test {
    public static void main(String args[]) {
        mysqldbService ms =new mysqldbService();

        String path="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=false";
        String username="root";
        String password="";
       if (ms.testConnection(path,username,password)){
           ms.getTableColumns(ms.getTables().stream().findFirst().get()).forEach((k,v)->System.out.println("Name : " + k + " Type : " + v));
       }


    }
}
