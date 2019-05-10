package FinalProj;
import java.sql.Connection;

import java.sql.DriverManager;
public class dbConnect {
	public Connection get_connection() {
		Connection conn=null;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManage?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root", "posiedon3");
	} catch (Exception e) {
		System.out.println(e);
	}
	return conn;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		dbConnect DBconnector = new dbConnect();
		System.out.println(DBconnector.get_connection());

	}

}
