package UI;

 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import java.awt.FlowLayout;

import java.awt.Font;

import java.awt.GridLayout;
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
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.StringTokenizer;
import java.util.Vector;

 

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import javax.swing.JTable;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mysql.jdbc.RowData;

import DB.Driver_connect;
//주문하는 GUI

public class GUI3new extends JFrame{

	Vector<Vector<String>> v =new Vector<Vector<String>>();//선택한 상품정보 벡터
	private Vector<btnv> btnv = new Vector<btnv>(); //왼쪽 버튼  메뉴이름 가격
	private Connection con = null;
	private PreparedStatement psmt = null;
	private JButton btn[] = new JButton[9];//1~9 버튼
	private Vector<Vector<String>> rowDate;
	private Vector<String> colDate;
	private String colD[]= {"상품번호","품명","수량","금액"};
	private JTable table;
	private int totalsum=0;
	ResultSet rs=null;
	
	private JLabel la[] = new JLabel[2]; //수량라벨
	private String sname[] = {"선택품명:","수량:"}; //선택수량필드
	private JTextField stext[] =new JTextField[2];	//수량 받는 텍필

	private JButton submit;	//누르면 수량 텍스트필드로 들어가기
	private JButton reset;	//누르면 수량 필드 초기화
	private JButton pay;
	private JButton cancle;
	private JButton zero = new JButton("0");
	private JLabel won=null; //위에 원바꾸끼
	
	private JComboBox<String> combo = null;	//사원번호 콤보
	private DefaultTableModel model=null; //테이블 모델
	private String a;
	public GUI3new(String b) {
		a=b;
		setTitle("결제");
		
		Container c = getContentPane();
		c.add(new northPanel(b),BorderLayout.NORTH);//한식 중식 양식 일식 레이블 나오는곳
		c.add(new centerPanel(b),BorderLayout.CENTER);//메뉴버튼이랑 수량 버튼 나누는곳

		setSize(1000,750);
		setVisible(true);
	}

	class northPanel extends JPanel{ //제목 레이블
		public northPanel(String b) { 
			setBackground(Color.white);
			JLabel title = new JLabel("한식") ;
			if(b.equals("1")) {
				title = new JLabel("한식");
			}else if(b.equals("2")) {
				title = new JLabel("중식");
			}
			else if(b.equals("3")) {
				title = new JLabel("일식");
			}

			else if(b.equals("4")) {
				title = new JLabel("양식");
			}

			title.setFont(new Font("돋움",Font.BOLD,20));
			add(title);

		}

	}

	class centerPanel extends JPanel{ 

		public centerPanel(String b) {
			setLayout(new BorderLayout());

			add(new centerPaner(b),BorderLayout.CENTER); //5*6 버튼넣는곳
			add(new eastPanel(),BorderLayout.EAST); //수량 총금액 넣는곳
		}

	}

	class centerPaner extends JPanel{
		
		public centerPaner(String b) {	//5*6버튼 넣기 내일 꺼 복붙하기152.70.250.228
//http://localhost:8081
			String url="http://152.70.250.228/meal/count/"+b;
				try {

					 JSONObject json = readJsonFromUrl(url);
					 JSONArray dataArray = (JSONArray)json.get("data");
					
						int a = Integer.parseInt((String) json.get("item"));

						if(a%5==0) {
							setLayout(new GridLayout(a/5,5));
						}else {
							setLayout(new GridLayout(a/5+1,5));
						}
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				makebutton(b);
			
				for(int a =0;a<btnv.size();a++) {
					add(btnv.get(a));
				}
				
				
				
		}
		

	  
		public void makebutton(String b) {
			String url="http://152.70.250.228/meal/item/"+b;
			
			try {
				
				 JSONObject json = readJsonFromUrl(url);
				JSONArray dataArray = (JSONArray)json.get("data");
				JSONObject obj = null;
	
				 for(int i=0;i<dataArray.size();i++) {
			        		obj = (JSONObject) dataArray.get(i);
			        	 
			        	 String a =(String) obj.get("mealName");
							String aa =Long.toString((Long)obj.get("price"));
							Long c =(Long) obj.get("maxCount");
							Long d =(Long) obj.get("todayMeal");
			        	btnv.add(new btnv(a,aa,c,d));
			        	
			        	
//			        	String aaaa= won.getText();
//			        	 totalsum = Integer.parseInt(aaaa) + wowo;
			        }
//				 int wowo =(int) obj.get("price");
//		        	System.out.println(wowo);
//				 String newsum = NumberFormat.getInstance().format(totalsum);
//				 won.setText(newsum+"원");
//				
			}catch(Exception ee) {
				ee.printStackTrace();
				System.out.println("btn sql 오류");
			}

		}
		

	}
	class btnv extends JButton{	//메뉴선택 버튼
		String name;
		public btnv(String name,String price,Long max,Long today) {
			this.name = name;
			this.setText("<html><center>"+name+"<br><br>"+price+"원"+"</center></html>");
			if(today==0||max<1) {
				this.setEnabled(false);
			}
			
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton falsebtn = (JButton)e.getSource();
					
					stext[0].setText(name);
					falsebtn.setEnabled(false);
				}
			});
		}
		public String getName() {
			return name;
		}
	}
	
	class eastPanel extends JPanel{
		public eastPanel() {	//수량 총금액에서 또 나누기
			setLayout(new BorderLayout());
			add(new wnorthP(),BorderLayout.NORTH);
			add(new wsouthP(),BorderLayout.CENTER);
		}

	}

	class wnorthP extends JPanel{

		public wnorthP() {	//총 원 과 테이블 ㄴ오기
			setLayout(new BorderLayout());
			add(new rightNorthP(),BorderLayout.NORTH);
			add(new rightCenterP(),BorderLayout.CENTER);
		}

	}

	class rightNorthP extends JPanel{

		public rightNorthP() {	//총금액 레이블
			
			setLayout(new GridLayout(1,2));
			JPanel jp = new JPanel();
			jp.setBackground(Color.white);
			this.setBackground(Color.white);
			jp.setLayout(new FlowLayout(FlowLayout.LEFT));

			JLabel all = new JLabel("총 결제금액: ");
			all.setFont(new Font("돋움",Font.PLAIN,15));	

			jp.add(all); 
			won = new JLabel("0원");
			JPanel jp2 = new JPanel();
			jp2.setBackground(Color.white);
			jp2.setLayout(new FlowLayout(FlowLayout.RIGHT));
			won.setFont(new Font("돋움",Font.PLAIN,15));	
			jp2.add(won);
			add(jp);  add(jp2);
		}
	}
	class rightCenterP extends JPanel{
		public rightCenterP() {	//테이블 나오는곳
			
			setBackground(Color.white);
			rowDate = new Vector<Vector<String>>();
			colDate = new Vector<String>();
			
			for(int i=0;i<colD.length;i++) {
				colDate.add(colD[i]);
			}

			model = new DefaultTableModel(rowDate,colDate);
			table = new JTable(model);
			JScrollPane jsp = new JScrollPane(table);
			add(jsp);
			
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if(e.getClickCount()==2) {
						int selectrow = table.getSelectedRow();
						
						Vector<String> click = rowDate.get(selectrow);
						int clickprice = Integer.parseInt(click.get(3));
						
						
						totalsum = totalsum-clickprice;
						String newsum = NumberFormat.getInstance().format(totalsum);
						
						won.setText(newsum+"원");
						
						for(int i =0;i<btnv.size();i++) {
							JButton jbtn = btnv.get(i);
							String clickTwo = click.get(1);
							
							if(jbtn.getName().equals(clickTwo)) {
								jbtn.setEnabled(true);
							}
						}
						rowDate.remove(selectrow);
						table.updateUI();
					}
				}
			});
			
			setVisible(true);
		}

	}

	class wsouthP extends JPanel{
		public wsouthP() {	//입력 숫자 버튼과 수량 텍스트 필드
			setLayout(new BorderLayout());
			add(new rsNorth(),BorderLayout.NORTH);
			add(new rsCenter(),BorderLayout.CENTER);
			add(new rsSouth(),BorderLayout.SOUTH);	//결제

		}

	}

	class rsNorth extends JPanel{	 //text수량필드
		public rsNorth() {	
			setBackground(Color.white);
			for(int i = 0;i<sname.length;i++) {
				la[i]=new JLabel(sname[i]);
				
				if(i==0) {
					stext[i] = new JTextField(20);
				}else
					stext[i] = new JTextField(10);
				
				stext[i].setEnabled(false);
				add(la[i]);
				add(stext[i]);

			}
		}

	}

	class rsCenter extends JPanel{	//버튼
		public rsCenter() {	
			setLayout(new BorderLayout());
			add(new calC(),BorderLayout.CENTER); // 숫자랑 입력창
			add(new calE(),BorderLayout.EAST); //결제 취소
		}

	}

	class calC extends JPanel{
		public calC() {
			setLayout(new BorderLayout());
			add(new cc_center(),BorderLayout.CENTER);	//슛자
			add(zero,BorderLayout.SOUTH);//0button
			zero.addActionListener(new countA());
		}
	}
	class cc_center extends JPanel{	//숫자 1-9넣는곳
		public cc_center() {
			setLayout(new GridLayout(3,3));

			for(int i=0; i<9; i++) {
				String a = Integer.toString(i+1);
				btn[i] = new JButton(a); 
				btn[i].addActionListener(new countA());
				add(btn[i]);
			}

		}

	}
	class countA implements ActionListener{	//숫자 버튼 누르면 수량 텍스트란에 입력
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton countBtn = (JButton)e.getSource();
			int btnc = Integer.parseInt(countBtn.getText());
			String read;
			String first;
			
			read = stext[1].getText();
			first = read + btnc;
			if(read.length()<=2) {
				stext[1].setText(first);
			}
			
			
		}
	}
	class calE extends JPanel{	//동쪽에 입력초기화 넣어주기
		public calE() {
			setLayout(new BorderLayout());
			submit = new JButton("입력");//계산기 입력 버튼
			submit.addActionListener(new nbtn());
			
			reset = new JButton("초기화");//초기화 버튼
			reset.addActionListener(new nbtn());
			
			add(submit,BorderLayout.CENTER);//입력 버튼
			add(reset,BorderLayout.SOUTH);//초기화 버튼
		}

	}
	class nbtn implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton btn = (JButton)e.getSource();

			if(btn.getText().equals("초기화")) {
				
				stext[0].setText("");
				stext[1].setText("");
			}
			if(btn.getText().equals("입력")) {
				
				if(stext[0].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "품명을 선택해주세요","message",JOptionPane.ERROR_MESSAGE);
				}else if(stext[1].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "품명을 선택해주세요","message",JOptionPane.ERROR_MESSAGE);
				}
				 //입력하면 테이블
				String url = "http://152.70.250.228/meal/search/"+URLEncoder.encode(stext[0].getText());
					try {
					int maxcount = 0;
					
					Vector<String> vt = new Vector<String>(); //table 벡터
//				
						JSONObject json = readJsonFromUrl(url);
						
						JSONArray dataArray = (JSONArray)json.get("data");
						String num =null;String price = null;
					JSONObject obj = null;
					 for(int i=0;i<dataArray.size();i++) {
			        		obj = (JSONObject) dataArray.get(i);
					
			        	num =Long.toString((Long)obj.get("mealNo"));
						price =Long.toString((Long)obj.get("price"));
						
						
					 }
//				        DecimalFormat ff = new DecimalFormat("#,###"); ff.format(totalsum)
					 totalsum = totalsum + (Integer.parseInt(price) * Integer.parseInt(stext[1].getText()));
					 
						won.setText(totalsum+"원");
						int sum = Integer.parseInt(stext[1].getText())*Integer.parseInt(price);
						vt.add(num);			
						vt.add(stext[0].getText());
						
						vt.add(stext[1].getText());
						vt.add(Integer.toString(sum));
						
						rowDate.add(vt);
					 table.updateUI();
					 stext[0].setText("");
						stext[1].setText("");
					}catch(Exception e1) {
						e1.printStackTrace();
					}
				}//else
			}
	}
	class rsSouth extends JPanel{	//결제 취소
		private JLabel sawonnum = new JLabel("사원번호");
		
		private Vector<String> vv =new Vector<String>();
		
		private JLabel password = new JLabel("패스워드");
		private JPasswordField jtxt = new JPasswordField(10);
		
		public rsSouth() {

			setLayout(new GridLayout(1,2));
			pay = new JButton("결제");
			pay.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					int click = JOptionPane.showConfirmDialog(null, new paylabel(),"결제자 인증",JOptionPane.OK_CANCEL_OPTION);
					if(click == JOptionPane.OK_OPTION) {
						try {
						Connection con = Driver_connect.makeConnection("meal");
						String id = combo.getSelectedItem().toString();
						String pass = new String(jtxt.getPassword());
						
						String url="http://152.70.250.228/member/find/"+id;
//						String sql = "select * from member where memberNo='"+id+"' and passwd='"+pass+"'";
//						PreparedStatement psmt = con.prepareStatement(sql);
						 JSONObject json = readJsonFromUrl(url);
							JSONArray dataArray = (JSONArray)json.get("data");
							JSONObject obj = null;String aa = null;
							for(int i=0;i<dataArray.size();i++) {
								obj = (JSONObject) dataArray.get(i);
							
								aa=(String) obj.get("passwd");

							}
							 
						
						if(aa.equals(pass)) {
							JOptionPane.showMessageDialog(null,"결제가 완료되었습니다.\n식권을 출력합니다.","Message",JOptionPane.INFORMATION_MESSAGE);
							new GUI4(model,id,a);
						}else {
							JOptionPane.showMessageDialog(null,"패스워드가 일치하지 않습니다.","Message",JOptionPane.OK_OPTION);
						}
						}catch(Exception eee) {
							eee.printStackTrace();
						}

						
					}
				}
			});
			cancle = new JButton("취소");
			add(pay);	add(cancle);
		}

	class paylabel extends JPanel{
		public paylabel() {
			setLayout(new GridLayout(2,2));
			
			try {
				
				String url="http://152.70.250.228/member/list";
				
				JSONObject json = readJsonFromUrl(url);
				JSONArray dataArray = (JSONArray)json.get("data");
				JSONObject obj = null;
				for(int i=0;i<dataArray.size();i++) {
					obj = (JSONObject) dataArray.get(i);
//					System.out.println(obj.get("memberNO"));
					String aa =Long.toString((Long)obj.get("memberNO"));
					vv.add(aa);
				}
				for(int i=0;i<vv.size();i++) {
					combo = new JComboBox(vv);
				}
				add(sawonnum);
				add(combo);
				add(password);
				add(jtxt);
				
			}catch(Exception es) {
				es.printStackTrace();
			}
			setSize(100,100);
			setVisible(true);
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
//	public static void main(String[] args) {
//		new GUI3new();
//
//	}

}