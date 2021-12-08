package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DB.Driver_connect;

public class GUI11 extends JFrame{
	private String sawon[] = {"사원번호:","*사원명:"};
	private String passp[]={"*패스워드:","*패스워드 재입력:"};
	private JTextField jf[] = new JTextField[4];
	private JPasswordField jpass[] = new JPasswordField[2];
	private String sanumber;
	
	private JLabel hide = new JLabel("");
	private String pw=null;
	private String pw2;
	
	private JButton btn[] = new JButton[2];
	private String btnst[] = {"등록","닫기"};
	
	Connection con;
	Statement st;
	ResultSet rs;
	
	public GUI11() {
		setTitle("사원등록");
		
		Container c = getContentPane();
		
		c.add(new Center(),BorderLayout.CENTER);
		c.add(new South(),BorderLayout.SOUTH);
		
		setSize(400,300);
		setVisible(true);
	}
	
	class Center extends JPanel{
		public Center() {
			setLayout(new GridLayout(4,2));
			
			for(int i=0;i<sawon.length;i++) {
				add(new JLabel(sawon[i]));
				add(jf[i] = new JTextField(10));
			}
			
				add(new JLabel(passp[0]));
				add(jpass[0]= new JPasswordField(10));
				
				add(new rowpass());
				add(jpass[1]= new JPasswordField(10));
				
				jpass[1].addActionListener(new MyEquals()); //불일치 해주기
				
				jpass[0].addKeyListener(new KeyOver());
				jpass[1].addKeyListener(new KeyOver());

			try {
				
				String url="http://localhost:8081/member/count";
				
				 JSONObject json = readJsonFromUrl(url);
//					System.out.println(json.toString());
					JSONArray dataArray = (JSONArray)json.get("data");
					int a = Integer.parseInt((String) json.get("item"));
	
					sanumber = Integer.toString(a+1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			jf[0].setText(sanumber);
			jf[0].setEnabled(false);
			
		}
	}
	class rowpass extends JPanel {
		public rowpass() {
			setLayout(new GridLayout(1,2));
			
			add(new JLabel(passp[1]));
			add(hide);
		}

	}
	class KeyOver extends KeyAdapter{
		@Override
		public void keyReleased(KeyEvent e) {
			JPasswordField jpf = (JPasswordField)e.getSource();
			String a = new String(jpf.getPassword());
			int get =a.length();
			String s = "^[0-9 a-z A-Z]*$";
			//^[a-zA-Z0-9]*$
			
			if(get<=4 && a.matches(s)) {
				
			}else {
				System.out.println("4글자미만 영문자 숫자 입력");
				jpass[0].setText("");
				jpass[1].setText("");
			}
		}
	}
	class MyEquals implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			pw = new String(jpass[0].getPassword());
			pw2 = new String(jpass[1].getPassword());
			
			if(pw.equals(pw2)) {
				hide.setForeground(Color.BLUE);
				hide.setText("일치");
			}
			else {
				hide.setForeground(Color.RED);
				hide.setText("불일치");
			}
			hide.setVisible(true);
		}
	}
	class South extends JPanel{
		public South() {
			setLayout(new GridLayout(1,2));
			for(int i=0;i<btnst.length;i++) {
				btn[i] = new JButton(btnst[i]);
				btn[i].addActionListener(new MYAction());
				add(btn[i]);
			}
		}
	}
	class MYAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
			case "등록" :	isSet();break;
			case "닫기" : dispose();break;
			}
			
		}
	}
	private void isSet() {
		pw = new String(jpass[0].getPassword());
//		
//		if(jf[1].getText().equals("") || pw.equals("") || pw2.equals("")) {
//			JOptionPane.showMessageDialog(null, "필수 항목(*) 누락","Message",JOptionPane.ERROR_MESSAGE);
//		}else if(!pw.equals(pw2)) {
//			JOptionPane.showMessageDialog(null, "패스워드 확인 요망","Message",JOptionPane.ERROR_MESSAGE);
//		}else {
			try {
				System.out.println(jf[1].getText());
				String url="http://localhost:8081/member/insert"+"?memberName="+URLEncoder.encode((String)jf[1].getText())+"&passwd="+URLEncoder.encode((String)pw);
//				String url="http://localhost:8081/member/insert/"+URLEncoder.encode((String)jf[1].getText())+"/"+URLEncoder.encode((String)pw);
				JSONObject json = readJsonFromUrl(url);
				JSONArray dataArray = (JSONArray)json.get("data");
				JSONObject obj = null;
				JOptionPane.showMessageDialog(null, "사원이 등록되었습니다.","Message",JOptionPane.INFORMATION_MESSAGE);;
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//	}
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
//	public static void main(String args[]) {
//		new GUI11();
//	}
}
