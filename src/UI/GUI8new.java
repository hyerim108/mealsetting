package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DB.Driver_connect;
import UI.GUI3new2.btnv;


//메뉴관리
public class GUI8new extends JFrame{
	private JComboBox<String> com = new JComboBox<String>();
	private String coms[] = {"한식","중식","일식","양식"}; //northp의 콤보박스
	
	private JButton btn[] = new JButton[5];
	private String btns[] = {"검색","수정","삭제","오늘의 메뉴선정","닫기"}; //northp 버튼들
	
	private DefaultTableModel model;
	private Vector<Vector<Object>> rowData;
	private Vector<String> colData;
	private JTable table;
	private String colD[] = {"ㅁ","menuName","price","maxcount","todayMeal"}; //centerp 테이블
	
	private Vector<Object> v; //table에 넣을 내용 벡터들
	
	private int select; //table click 열
	int i = 0;
	
	public GUI8new() {
		setTitle("메뉴 관리");
		Container c = getContentPane();
		
		c.add(new northp(),BorderLayout.NORTH);
		c.add(new centerp(),BorderLayout.CENTER);
		
		setSize(500,520);
		setVisible(true);
	}
	
	class northp extends JPanel{
		private JLabel la = new JLabel("종류:");
		
		public northp() {
			add(la);
			for(int i =0;i<coms.length;i++) {
				com.addItem(coms[i]);
				add(com);
			}
			for(int i =0;i<btns.length;i++) {
				btn[i] = new JButton(btns[i]);
				btn[i].addActionListener(new MYAction());
				add(btn[i]);
			}
			
		}
	}
	class centerp extends JPanel{
		public centerp() {	//centerpanel 테이블
			rowData = new Vector<Vector<Object>>();
			colData = new Vector<String>();
			
			for(int i = 0 ;i<colD.length;i++) {
				colData.add(colD[i]);
			}
			
			model = new DefaultTableModel(rowData,colData) {
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					switch(columnIndex) {
						case 0:return Boolean.class;
						default: return String.class;
					
					}
				}
			};
			
			table = new JTable(model);
			JScrollPane jsp = new JScrollPane(table);
			add(jsp);
			search();
			
//			table.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mouseClicked(MouseEvent e) {
//					select = table.getSelectedRow();
//					v= rowData.get(select);
//				}
//			});
		}
	}
	class MYAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) { //버튼에 넣기
			String cmd = e.getActionCommand();
			switch(cmd){
				case "검색": search();break;
				case "수정" : fresh(); break;
				case "삭제" :delete();break;
				case "오늘의 메뉴선정" :isToday();break;
				case "닫기" : dispose();break;
			}
		}
	}
	private void search() {
		String No = com.getSelectedItem().toString();
		
		
		if(No.equals("한식")) {
			i=1;
		}else if(No.equals("중식")) {
			i=2;
		}else if(No.equals("일식")) {
			i=3;
		}else if(No.equals("양식")) {
			i=4;
		}
		String name ;
		String sql = "select mealName, price, maxCount, todayMeal from meal where cuisineNo = " + i;
		String url="http://localhost:8081/meal/item/"+i;
		try {
			rowData.clear();
//			Connection con = Driver_connect.makeConnection("meal");
//			
//			Statement stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery(sql);
//			
			 JSONObject json = readJsonFromUrl(url);
//			System.out.println(json.toString());
			JSONArray dataArray = (JSONArray)json.get("data");
//				 System.out.println(json.get("mealName"));
			JSONObject obj = null;
			for(int i=0;i<dataArray.size();i++) {
        		obj = (JSONObject) dataArray.get(i);
        		 System.out.println(obj.get("mealName"));
	        	 System.out.println(obj.get("price"));
	        	 System.out.println(obj.get("maxCount"));
	        	 System.out.println(obj.get("todayMeal"));
	        	 
	        	 String a =(String) obj.get("mealName");
					String aa =Long.toString((Long)obj.get("price"));
					Long c =(Long) obj.get("maxCount");
					Long d =(Long) obj.get("todayMeal");
        		v = new Vector<Object>();
				v.add(false);
				
				v.add(a);
				v.add(aa);
			
				v.add(c);
				 if(d == 1) {
					 v.add("Y");
				 }else {
					v.add("N");
					}
				rowData.add(v);
        }
	
			
			table.updateUI();
			
		}
		catch(Exception e) {
			System.out.println("오류다");
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
	private void fresh() {
		int check =0;
		int u=0;
		for(int i =0;i<model.getRowCount();i++) {
			if((boolean)model.getValueAt(i, 0)) {
				u=i;
				check++;
			};
		}
		String b[] = new String[4];
		if(check == 1){
			b[0] = com.getSelectedItem().toString();
			b[1] = table.getValueAt(u, 1).toString();
			b[2] = table.getValueAt(u, 2).toString();
			b[3] = table.getValueAt(u, 3).toString();
			new GUI8f(b);
		}else if(check>=2) {
			JOptionPane.showMessageDialog(null, "하나씩 수정가능합니다.","Message",JOptionPane.ERROR_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "수정할 메뉴를 선택해주세요..","Message",JOptionPane.ERROR_MESSAGE);
		}
	}
	private void delete() {
		String sql = "delete from meal where mealName='";
		try {
			Connection con = Driver_connect.makeConnection("meal");
			Statement st = con.createStatement();
			for(int i =0;i<model.getRowCount();i++) {
				if((boolean)model.getValueAt(i, 0)) {
					String g = sql + model.getValueAt(i, 1) +"'";
					st.executeUpdate(g);
				};
			}
			
		}catch(SQLException e2) {
			System.out.println("del sql 오류");
		}
		search();
	}
	public void isToday() {
		
		int cnt = 0;
		Vector<String> v = new Vector<String>();
		for(int i=0;i<model.getRowCount();i++) {
			if((boolean)model.getValueAt(i, 0)) {
				v.add((String)model.getValueAt(i, 1));
				cnt++;
			}
		}
		if(cnt>25) {
			JOptionPane.showMessageDialog(null, "25개를 초과할 수 업습니다..","Message",JOptionPane.ERROR_MESSAGE);
		}else {
			
			String sql2 = "update meal set todayMeal=1 where mealName='";
			String sql = "update meal set todayMeal=0 where mealNo="+i;
			try {
				Connection con = Driver_connect.makeConnection("meal");
				Statement st = con.createStatement();
				st.executeUpdate(sql);
				
				for(int i=0;i<v.size();i++) {
					String g = sql2 + v.get(i)+"'";
					st.executeUpdate(g);
				}
			}catch(SQLException e) {
				System.out.println("isToday 오류");
			}
		}
		search();
	}
//	public static void main(String args[]) {
//		new GUI8();
//	}
}
