package DB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.StringTokenizer;


public class insertMeal {
	String file[] = {"cuisine.txt","meal.txt","member.txt","orderlist.txt"};
	public insertMeal() {
		Statement st = null;
		Connection con = Driver_connect.makeConnection("meal");
		PreparedStatement psmt;
		String name[] = {"cuisine","meal","member","orderlist"};
		String q[] = {"(?,?)","(?,?,?,?,?,?)","(?,?,?)","(?,?,?,?,?,?,?)"};
		String a ="";
		for(int i=0;i<name.length;i++) {
			
			try {
				Scanner sc = new Scanner(new FileInputStream(".\\DataFiles\\"+file[i]));
				
				sc.nextLine();
				while(sc.hasNext()) {
					String d = sc.nextLine();
					//데이터 출력해보기
					StringTokenizer stn = new StringTokenizer(d,"\t");
					
					a = "insert into " + name[i]+ " values" + q[i];
					psmt = con.prepareStatement(a);
					
					int count = stn.countTokens();
					for(int j=0;j<count;j++) {
						psmt.setString(j+1, stn.nextToken());
					}
					int re = psmt.executeUpdate();
					if(re ==1) {
					}else {
						System.out.println("추가실패");
					}
				}
			}catch(FileNotFoundException e) {
				System.out.println("insert file 오류");
			}catch(SQLException ee) {
				System.out.println("");
			}
		}
	}
	public static void main(String[] arg) {
//		new insertMeal();
	}
}
