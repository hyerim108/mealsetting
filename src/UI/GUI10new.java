package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import com.mysql.fabric.xmlrpc.base.Data;

import DB.Driver_connect;

//종류별 차트
public class GUI10new extends JFrame{
	private JButton btn[] = new JButton[2];
	private String jbs[] = {"차트이미지저장","닫기"};
	private String[] item = { "Apple", "Cherry", "Strawberry", "Prune" }; 

	private Color c[] = new Color[4];
	private int[] data = new int[4]; //수치data배열
	private int[] arc = new int[4]; //각도퍼센트배열
	private String cname[] = {"한식","중식","일식","양식"};
	private int sum;
	public GUI10new() {
		setTitle("종류별 결제현황차트");
		
		Container c = getContentPane();
		
		c.add(new Northp(),BorderLayout.NORTH);
		c.add(new Centerp(),BorderLayout.CENTER);
		
		setSize(500,400);
		setVisible(true);
	}
	class Northp extends JPanel{
		public Northp() {
			add(new JLabel("종류별 결제건수 통계차트 "));
			
			for(int i=0;i<jbs.length;i++) {
				btn[i] = new JButton(jbs[i]);
				btn[i].addActionListener(new MYAction());
				add(btn[i]);
			}
		}
	}
	class Centerp extends JPanel{
		public Centerp() {
			setLayout(null);
			
				try {
//					String sql = "select cuisineNo from orderlist";
					
					String url = "http://localhost:8081/order/list";
					
					JSONObject json = readJsonFromUrl(url);
					JSONArray dataArray = (JSONArray)json.get("data");
					JSONObject obj = null;	
					
					for(int i=0;i<dataArray.size();i++) {
						obj = (JSONObject) dataArray.get(i);
						
						String No = Long.toString((Long)obj.get("menuNo"));									
						System.out.println(No);
						int num = Integer.parseInt(No);
						
						
						if(num==1) {
							data[0]++;
						}else if(num==2) {
							data[1]++;
						}else if(num==3) {
							data[2]++;
						}else data[3]++;	
						
					}	
					sum += data[0]+data[1]+data[2]+data[3];		
					
					for(int j =0;j<data.length;j++) {
						arc[j] = (int)Math.round((float)data[j]/(float)sum*360);
						cname[j]= cname[j]+ "(" + Integer.toString(data[j]) + "건)";
					}

					
				}catch(Exception e) {
					System.out.println("오류"+e);
				}
			}
		
		
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			int starta = 0;
			int r,gr,b;
			for(int i=0;i<data.length;i++) {
				r = (int)(Math.random()*256);
				gr = (int)(Math.random()*256);
				b=(int)(Math.random()*256);
				c[i]=new Color(r,gr,b);
				g.setColor(c[i]);
				g.fillArc(100, 30, 250, 250, starta, arc[i]);
				starta = starta +arc[i];
			}
			for(int i=0;i<data.length;i++) {
				g.setColor(c[i]);
				g.fillRect(390, 160+(i*20), 10, 10);
				g.setColor(Color.BLACK);
				g.drawString(cname[i], 400, 170+(i*20));
			}
		}
	}
	class MYAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
			case "차트이미지저장" : imgsave();break;
			case "닫기" : dispose();break;
			}
			
		}
	}
	public void imgsave() {
		SimpleDateFormat format = new SimpleDateFormat("yyyymmddhhmmss");
		Date time = new Date();
		String top = format.format(time);
		
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphic = image.createGraphics();
		paint(graphic);
		try {
			ImageIO.write(image,"jpg",new File(top+"-종류별결제현황차트.jpg"));
			
		}catch(IOException e) {
			System.out.println("차트이미지 저장 오류");
		}
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
	private String jsonReadAll(Reader reader) throws IOException{
        StringBuilder sb = new StringBuilder();

        int cp;
        while((cp = reader.read()) != -1){
            sb.append((char) cp);
        }

        return sb.toString();
    }
	
}
