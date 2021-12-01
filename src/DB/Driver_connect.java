package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Driver_connect {
	public static Connection makeConnection(String s) {
		String url = "jdbc:mysql://localhost/"+s;
		String id = "root";
		String pw = "root1234!";
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("드라이브어쩌구 성공");
			con = DriverManager.getConnection(url,id,pw);
			System.out.println("데이터베이스 연결성공");
		}catch(ClassNotFoundException e) {
			System.out.println("드라이브 찾을 수 없습니다.");
		}catch(SQLException e) {
			System.out.println("연결실패!!");
		}
		return con;
	
	}
	public static void main(String[] arg) {
		Connection con = makeConnection("");
	}
}
