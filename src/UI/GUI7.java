package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DB.Driver_connect;

public class GUI7 extends JFrame{
	private String text[] = {"종류","*메뉴명","가격","조리가능수량"};
	private JTextField jt = new JTextField();
	private String btns[] = {"등록", "닫기"};
	private JButton btn[] = new JButton[2];
	
	private JComboBox<String> combo1 = new JComboBox<String>();
	private String com1[] = {"한식","중식","일식","양식"};
	
	private JComboBox<String> combo2 = new JComboBox<String>();
	private JComboBox<String> combo3 = new JComboBox<String>();
	
	public GUI7() {
		setTitle("신규 메뉴 등록");
		
		Container c = getContentPane();
		c.add(new Cenp(),BorderLayout.CENTER);
		c.add(new Soup(),BorderLayout.SOUTH);
		
		setSize(450,300);
		setVisible(true);
	}
	class Cenp extends JPanel{
		public Cenp() {
			setLayout(new GridLayout(4,2));
			
			for(int i =0;i<text.length;i++) {
				add(new JLabel(text[i]));

				if(i==0) {
					for(int j=0;j<com1.length;j++) {
						combo1.addItem(com1[j]);
						add(combo1);
					}
				}
				else if(i==2) {
					for(int c=1000;c<12001;c++) {
						if(c%500==0) {
							String q = Integer.toString(c);
							combo2.addItem(q);
							add(combo2);
						}
					}
				}
				else if(i==3) {
					for(int j=0;j<51;j++) {
						String a =Integer.toString(j);
						combo3.addItem(a);
						add(combo3);
					}
				}
				else 
					add(jt=new JTextField(10));
				
			}
			
		}
	}
	class Soup extends JPanel{ //등록 닫기
		public Soup() {
			setLayout(new GridLayout(1,2));
			
			for(int i = 0;i<btns.length;i++) {
				btn[i]= new JButton(btns[i]);
				add(btn[i]);
				btn[i].addActionListener(new MyAction());
			}
		}
	}
	class MyAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String a  = e.getActionCommand();
			
			String no,menuname,n,m;
			int price,count,number,today = 0,cuisine=0;
			
			no = combo1.getSelectedItem().toString();
			
			if(no.equals("한식")) {
				cuisine=1;
			}else if(no.equals("중식")) {
				cuisine=2;
			}else if(no.equals("일식")) {
				cuisine=3;
			}else if(no.equals("양식")) {
				cuisine=4;
			}
			
			menuname =jt.getText();
			
			n = (String)combo2.getSelectedItem();
			price = Integer.parseInt(n);
			
			m = (String)combo3.getSelectedItem();
			count = Integer.parseInt(m);
			
			try {
				Connection con = Driver_connect.makeConnection("meal");
				PreparedStatement psmt = null;
				String sql = "insert into meal values(?,?,?,?,?,?) ";
				
				String sql2 = "select max(mealNo) from meal";
				
				psmt = con.prepareStatement(sql);
				
				Statement st =con.createStatement();
				ResultSet rs =st.executeQuery(sql2);
				
				while(rs.next()) {
					number = Integer.parseInt(rs.getString(1))+1;
					System.out.println(number);
				
					if(a.equals("등록")) {
						if(jt.equals(" ")) {
							JOptionPane.showConfirmDialog(null, "메뉴명을 입력해주세요.","Message",JOptionPane.ERROR_MESSAGE);
						}else {
							
							psmt.setInt(1, number);psmt.setInt(2, cuisine);psmt.setString(3, menuname);
							psmt.setInt(4, price);psmt.setInt(5, count);psmt.setInt(6, today);
							
							int re = psmt.executeUpdate();
							if(re>0) {
								JOptionPane.showConfirmDialog(null, "메뉴가 정상적으로 등록되었습니다.","Message",JOptionPane.INFORMATION_MESSAGE);
								dispose();
							}
						}
					}else {
						dispose();
					}
				}
				psmt.close();
				con.close();
				rs.close();
			}catch(Exception e2) {
				
			}
			
			
		}
	}
//	public static void main(String args[]) {
//		new GUI7();
//	}
}
