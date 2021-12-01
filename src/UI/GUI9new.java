package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Date;
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

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DB.Driver_connect;

//결제조회
public class GUI9new extends JFrame{
	private JTextField jf = new JTextField();
	private String btns[] = {"조회","새로고침","인쇄","닫기"};
	private JButton btn[] = new JButton[4];
	
	private Vector<Vector<String>> rowData = new Vector<Vector<String>>();
	private Vector<String> colData;
	private String colD[] = {"종류","메뉴명","사원명","결제수량","총결제금액","결제일"};
	private JTable table;
	
	
	public GUI9new() {
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
		String url="http://localhost:8081/order/item/"+jf.getText();
//		String sql = "select meal.cuisineNo,meal.mealName,member.memberName,"
//				+ "orderCount,amount,orderDate from orderlist "
//				+"INNER JOIN member on "
//				+"orderlist.memberNo = member.memberNo "+
//				"JOIN meal on "
//				+"orderlist.mealNo = meal.mealNo "
//				+"where meal.mealName like '%"+ jf.getText() +"%'";
		rowData.clear();
		try {
			 JSONObject json = readJsonFromUrl(url);
				System.out.println(json.toString());
				JSONArray dataArray = (JSONArray)json.get("data");
				JSONObject obj = null;
				
				Vector<String> v = new Vector<String>();
				 for(int i=0;i<dataArray.size();i++) {
		        		obj = (JSONObject) dataArray.get(i);
		        		
		        		String a= Long.toString((Long)obj.get("menuNo"));
		        		String b= Long.toString((Long)obj.get("orderCount"));
		        		String c = (String) obj.get("memberName");
		        		String d = (String) obj.get("mealName");
		        		String e= Long.toString((Long)obj.get("amount"));
		        		String date = (String) obj.get("orderDate");
		        		
		        		//종류 메뉴명 사원명 결제수량 결제금액 결제일
		        		System.out.println(a+","+b+","+c+","+d+e+","+date);
		        		switch(a) {
						case "1" : v.add("한식");break;
						case "2" : v.add("중식");break;
						case "3" : v.add("일식");break;
						case "4" : v.add("양식");break;
		        		}
		        		v.add(c);
		        		v.add(d);
		        		v.add(b);
		        		v.add(e);
		        		v.add(date);
		        		
		        		rowData.add(v);
			}
			
			table.updateUI();
			
		}catch(Exception ee) {
			System.out.println();
		}
	}
	  private String jsonReadAll(Reader reader) throws IOException{
	        StringBuilder sb = new StringBuilder();

	        int cp;
	        while((cp = reader.read()) != -1){
	            sb.append((char) cp);
	        }

	        return sb.toString();
	    }

	    private JSONObject readJsonFromUrl(String url) throws IOException,JSONException{
	        InputStream is = new URL(url).openStream();

	        try{
	            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	            String jsonText = jsonReadAll(br);
//	            System.out.println(jsonText);
	            JSONObject json = new JSONObject();
	            JSONParser parser = new JSONParser();
	            try {
	            	Object obj = parser.parse(jsonText);
	            	json = (JSONObject)obj;
	            }catch(ParseException e) {
	            	e.printStackTrace();
	            }
	            return json;
	        } finally {
	            is.close();
	        }
	    }
	private void Setting() {	//새로고침
//		http://localhost:8081/order/item
		String url="http://localhost:8081/order/item";
		rowData.clear();
		jf.setText("");
		try {
			 JSONObject json = readJsonFromUrl(url);
				System.out.println(json.toString());
				JSONArray dataArray = (JSONArray)json.get("data");
				JSONObject obj = null;
				
				Vector<String> v = new Vector<String>();
				 for(int i=0;i<dataArray.size();i++) {
		        		obj = (JSONObject) dataArray.get(i);
		        		
		        		String a= Long.toString((Long)obj.get("menuNo"));
		        		String b= Long.toString((Long)obj.get("orderCount"));
		        		String c = (String) obj.get("memberName");
		        		String d = (String) obj.get("mealName");
		        		String e= Long.toString((Long)obj.get("amount"));
		        		String date = (String) obj.get("orderDate");
		        		
		        		//종류 메뉴명 사원명 결제수량 결제금액 결제일
		        		System.out.println(a+","+b+","+c+","+d+e+","+date);
		        		switch(a) {
						case "1" : v.add("한식");break;
						case "2" : v.add("중식");break;
						case "3" : v.add("일식");break;
						case "4" : v.add("양식");break;
		        		}
		        		v.add(c);
		        		v.add(d);
		        		v.add(b);
		        		v.add(e);
		        		v.add(date);
		        		
		        		rowData.add(v);
		        	}
				
			
			table.updateUI();
		}catch(Exception ee) {
			ee.printStackTrace();
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
