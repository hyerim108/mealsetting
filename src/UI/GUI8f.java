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
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DB.Driver_connect;


public class GUI8f extends JFrame{
	private String text[] = {"종류","*메뉴명","가격","조리가능수량"};
	
	private JButton btn[] = new JButton[2];
	private String btns[] = {"수정","닫기"};
	
	private JTextField jf= new JTextField();
	
	private JComboBox<String> com1 = new JComboBox<String>();
	private String combo[] = {"한식","중식","일식","양식"};
	
	private JComboBox<String> com2 = new JComboBox<String>();
	private JComboBox<String> com3 = new JComboBox<String>();
	
	private String b[];
	private int a[] = new int[3];
	
	public GUI8f(String b[]) {
		this.b=b;
		
		setTitle("메뉴 수정");
		
		Container c= getContentPane();
		
		c.add(new Centerp(),BorderLayout.CENTER);
		c.add(new Southp(),BorderLayout.SOUTH);
		
		setSize(400,280);
		setVisible(true);
	}
	class Centerp extends JPanel{
		public Centerp() {
			setLayout(new GridLayout(4,2));
			for(int i = 0;i<text.length;i++) {
				add(new JLabel(text[i]));
				if(i==0) {
					for(int j = 0;j<combo.length;j++) {
						if(b[0].equals(combo[j])) {a[0]=j;}
						com1.addItem(combo[j]);
						add(com1);
					}
				}else if(i==1) {
					
					jf=new JTextField(10);
					add(jf);
					
				}else if(i==2) {
					for(int c=1000;c<12001;c+=500) {
						if(b[2].equals(Integer.toString(c))){a[1]=c/500-2;}
							String q = Integer.toString(c);
							com2.addItem(q);
							add(com2);
						
					}
				}else if(i==3) {
					for(int j=0;j<51;j++) {
						if(b[3].equals(Integer.toString(j))){a[2]=j;}
						String a =Integer.toString(j);
						com3.addItem(a);
						add(com3);
					}
				}
				
			}
			
			com1.setSelectedIndex(a[0]);
			jf.setText(b[1]);
			com2.setSelectedIndex(a[1]);
			com3.setSelectedIndex(a[2]);
		}
	}
	class Southp extends JPanel{
		public Southp() {
			setLayout(new GridLayout(1,2));
			for(int i=0;i<btns.length;i++) {
				btn[i]=new JButton(btns[i]);
				btn[i].addActionListener(new myAction());
				add(btn[i]);
			}
		}
	}
	class myAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
				case "수정" : reset(); break;
				case "닫기" : break;
			}
			
		}
	}
	private void reset() {
//		String sql = "update meal set price="+com2.getSelectedItem()+",maxCount="+com3.getSelectedItem()+" where mealName='"+jf.getText()+"'";
//		String url="http://localhost:8081/meal/updateMenu/"+
//				URLEncoder.encode((String) com2.getSelectedItem())+"/"+
//				URLEncoder.encode((String) com3.getSelectedItem())+"/"+URLEncoder.encode((String) jf.getText());
		String url="http://localhost:8081/meal/updateMenu?price="+
				URLEncoder.encode((String) com2.getSelectedItem())+"&maxCount="+
				URLEncoder.encode((String) com3.getSelectedItem())+"&mealName="+URLEncoder.encode((String) jf.getText());
		try {
				JSONObject json = readJsonFromUrl(url);
			JOptionPane.showMessageDialog(null, "메뉴가 정상적으로 수정되었습니다.","Message",JOptionPane.QUESTION_MESSAGE);
			dispose();
		}catch(Exception e1) {
			e1.printStackTrace();
			System.out.println("업테이트 sql 오류");
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
}
