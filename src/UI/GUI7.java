package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	private int price,count,number,today = 0,cuisine=0;
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
			String menuNO= Integer.toString(cuisine);
			try {
				String url="http://152.70.250.228/meal/insert?menuNo="+URLEncoder.encode((String)menuNO)+
						"&mealName="+URLEncoder.encode((String)menuname)+
						"&price="+URLEncoder.encode(Integer.toString(price))+
						"&maxCount="+URLEncoder.encode(Integer.toString(count))+
						"&todayMeal="+URLEncoder.encode(Integer.toString(today));
		
				//이름 가격 맥스카운트 조리가능수량
					if(a.equals("등록")) {
//						if(jt.equals(" ")) {
//							JOptionPane.showConfirmDialog(null, "메뉴명을 입력해주세요.","Message",JOptionPane.ERROR_MESSAGE);
//						}else {
//							
							JSONObject json = readJsonFromUrl(url);
							JSONArray dataArray = (JSONArray)json.get("data");
							JSONObject obj = null;
							JOptionPane.showMessageDialog(null, "메뉴가 정상적으로 등록되었습니다.","Message",JOptionPane.INFORMATION_MESSAGE);
								
						}else {
							dispose();
						}
//					}
			}catch(Exception e2) {
				e2.printStackTrace();
			}
			
			
			
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
//            System.out.println(jsonText);
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
//	public static void main(String args[]) {
//		new GUI7();
//	}
}
