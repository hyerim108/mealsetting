package DB;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateMeal {
	public CreateMeal() {
		Connection con =null;
		String meal = "create database if not exists Meal";
		String[] create= {"create table if not exists member("
				+ "memberNo int not null primary key auto_increment,"
				+ "membername varchar(20),passwd varchar(4))",
				
				"create table if not exists cuisine("
				+ "cuisineNo int not null primary key auto_increment, cuisineName varchar(10))",
				
				"create table if not exists meal("
				+ "mealNo int not null primary key auto_increment,cuisineNo int,"
				+ "mealName varchar(20),price int,maxCount int,todayMeal tinyint(1))",
				
				"create table if not exists orderlist(orderNo int not null primary key auto_increment,"
				+ "cuisineNo int,mealNo int,memberNo int,orderCount int,amount int, orderDate datetime)"};
		
		try {
			con=Driver_connect.makeConnection("");
			Statement st = con.createStatement();
			st.executeUpdate(meal);
			con=Driver_connect.makeConnection("meal");
			for(int i =0; i<create.length;i++) {
				st = con.createStatement();
				st.executeUpdate(create[i]);
			}
			System.out.println("만들기 성공");
		}catch(SQLException e) {
			System.out.println("create sql오류");
		}
	}
	public static void main(String[] arg) {
		new CreateMeal();
		new insertMeal();
	}
}
