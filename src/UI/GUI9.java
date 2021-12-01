package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;

import DB.Driver_connect;

//결제조회
public class GUI9 extends JFrame{
	private JTextField jf = new JTextField();
	private String btns[] = {"조회","새로고침","인쇄","닫기"};
	private JButton btn[] = new JButton[4];
	
	private Vector<Vector<String>> rowData = new Vector<Vector<String>>();
	private Vector<String> colData;
	private String colD[] = {"종류","메뉴명","사원명","결제수량","총결제금액","결제일"};
	private JTable table;
	
	
	public GUI9() {
		setTitle("결제 조회");
		
		Container  c = getContentPane();
		c.add(new NorthP(),BorderLayout.NORTH);
		c.add(new CenterP(),BorderLayout.CENTER);
		
		Setting(); //실행했을떄 바로 테이블 보이게 하기
		
		setSize(480,510);
		setVisible(true);
	}
	class NorthP extends JPanel {
		public NorthP() {
			add(new JLabel("메뉴명:"));
			add(jf = new JTextField(10));
			for(int i = 0;i<btns.length;i++) {
				btn[i]=new JButton(btns[i]);
				btn[i].addActionListener(new MyAction());
				add(btn[i]);
			}
		}
	}
	class MyAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
			case "조회" : search();break;
			case "새로고침" : Setting();break;
			case "인쇄" : print();break;
			case "닫기" : dispose();
			}
		}
	}
	
	private void search() {	//조회를 클릭시
		String sql = "select meal.cuisineNo,meal.mealName,member.memberName,"
				+ "orderCount,amount,orderDate from orderlist "
				+"INNER JOIN member on "
				+"orderlist.memberNo = member.memberNo "+
				"JOIN meal on "
				+"orderlist.mealNo = meal.mealNo "
				+"where meal.mealName like '%"+ jf.getText() +"%'";
		rowData.clear();
		try {
			Connection con = Driver_connect.makeConnection("meal");
			Statement st=  con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				Vector<String> v = new Vector<String>();
				
				switch(rs.getString(1)) {
				case "1" : v.add("한식");break;
				case "2" : v.add("중식");break;
				case "3" : v.add("일식");break;
				case "4" : v.add("양식");break;
			}
			
				for(int i=2;i<7;i++) {
					v.add(rs.getString(i));
				}
				rowData.add(v);
			}
			
			table.updateUI();
			con.close();
			
		}catch(SQLException ee) {
			System.out.println();
		}
	}
	private void Setting() {	//새로고침
//		http://localhost:8081/order/item
		String sql = "select meal.cuisineNo,meal.mealName,member.memberName,"
				+ "orderCount,amount,orderDate from orderlist "
				+"INNER JOIN member on "
				+"orderlist.memberNo = member.memberNo "+
				"JOIN meal on "
				+"orderlist.mealNo = meal.mealNo";
		
		rowData.clear();
		jf.setText("");
		try {
			Connection con = Driver_connect.makeConnection("meal");
			Statement st=  con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				Vector<String> v = new Vector<String>();
				
				switch(rs.getString(1)) {
				case "1" : v.add("한식");break;
				case "2" : v.add("중식");break;
				case "3" : v.add("일식");break;
				case "4" : v.add("양식");break;
			}
				for(int i=2;i<7;i++) {
					v.add(rs.getString(i));
				}
				rowData.add(v);
			}
			
			table.updateUI();
			con.close();
		}catch(SQLException ee) {
			System.out.println();
		}
	}
	private void print() {
		FileDialog fileD = new FileDialog(this,"save",FileDialog.SAVE);
		fileD.setVisible(true);
		
		String path = fileD.getDirectory() + fileD.getFile();
		
		try {
			FileWriter fout = new FileWriter(path);
			fout.write("종류\t메뉴명\t사원명\t결제수량\t총결제금액\t결제일\r\n");
				
				for(int i=0;i<table.getRowCount();i++) {
					Vector<String> pv =rowData.get(i);
					
					for (String string : pv) {
						fout.write(string);
						fout.write("\t");
					}
					fout.write("\r\n");
				}
				
				
			fout.close();
		}catch(IOException e) {
			System.out.println("오류");
		}
	}
	class CenterP extends JPanel{
		public CenterP() {
			rowData = new Vector<Vector<String>>();
			colData = new Vector<String>();
			
			for(int i = 0;i<colD.length;i++) {
				colData.add(colD[i]);
			}
			
			DefaultTableModel model = new DefaultTableModel(rowData,colData);
			table = new JTable(model);
			JScrollPane jsp = new JScrollPane(table);
			add(jsp);
		}
	}
//	public static void main(String args[]) {
//		new GUI9();
//	}
}
