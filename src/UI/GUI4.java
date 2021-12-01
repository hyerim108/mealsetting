package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//식권 GUI
public class GUI4 extends JFrame{
	DefaultTableModel list = null;
	String id = null; //사원번호
	String menunum=null; //메뉴이름
	JTable table;
	JScrollPane sc;
	Vector<Vector> menu= new Vector<Vector>();
	String top=null;
	Vector v;
	int num=0;
	Color sky = new Color(174, 207, 254); 
	Color pink = new Color(248, 214, 231);
	Color white;
	public GUI4(DefaultTableModel model,String id,String a) {
		list = model;
		this.id = id;
		menunum = a;
		
		
		
		int count=0;
		int maxcount = list.getRowCount();
		for(int i =0;i<maxcount;i++) {
			count+=Integer.parseInt(list.getValueAt(i, 2).toString());
		}
		
		setLayout(new GridLayout(count,1,10,10));
		
		menu = list.getDataVector();
		Vector<border> bv = new Vector<border>();
		
		for(int i=0;i<menu.size();i++) {
			v = menu.get(i);
			String menuname = (String)v.get(1);
			int price = Integer.parseInt((String) v.get(3));
			num= Integer.parseInt((String)v.get(2));
			
			if(i%2==0) {
				for(int j=0;j<num;j++) {
					white = sky;
					bv.add(new border(menuname,price,j));
					}
				}else {
					for(int j=0;j<num;j++) {
					white = pink;
					bv.add(new border(menuname,price,j));
					}
				}
			}
		for(int i =0;i<bv.size();i++) {
			add(bv.get(i));
		}
		setSize(300,200*count);
		setVisible(true);
		
		makeScreenshot();
		
	}
	public void makeScreenshot() {
		BufferedImage image = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = image.createGraphics();
		paint(graphics);
		try {
			ImageIO.write(image,"jpg",new File(top+"-ticket.jpg"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	class border extends JPanel{
		String menuname;
		int price;
		public border(String menuname,int price,int j) {
			this.menuname = menuname;
			this.price = price;
			
			setLayout(new BorderLayout());
			
			add(new Bordern(),BorderLayout.NORTH);
			add(new Borderc(),BorderLayout.CENTER);
			add(new Borders(j),BorderLayout.SOUTH);
			
			
		}

	class Bordern extends JPanel{
		public Bordern() {
			setLayout(new GridLayout(1,1));
			JPanel jp = new JPanel();
			SimpleDateFormat format = new SimpleDateFormat("yyyymmddhhmmss");
			Date time = new Date();
			String ss = format.format(time);
			top= ss + "-" + id + "-" + menunum;
			
			jp.setLayout(new FlowLayout(FlowLayout.LEFT));
			jp.add(new JLabel(top));
			jp.setBackground(white);
			add(jp);
		}
	}
	class Borderc extends JPanel{
		public Borderc() {
			setLayout(new GridLayout(2,1));
			
			JLabel sik = new JLabel("식 권");
			sik.setHorizontalAlignment(JLabel.CENTER);
			
			DecimalFormat f= new DecimalFormat("#,##0");
			
			JLabel won = new JLabel(f.format(price)+"원");
			won.setHorizontalAlignment(JLabel.CENTER);
			
			Font ft = new Font("돋움", Font.BOLD, 30);
			sik.setFont(ft);
			won.setFont(ft);
			
			setBackground(white);
			add(sik);
			add(won);
		}
	}
	class Borders extends JPanel{
		public Borders(int j) {
			setLayout(new GridLayout(1,2));
			JPanel jp = new JPanel();
			JLabel menu = new JLabel("메뉴:"+menuname);
			jp.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			JPanel jp2 = new JPanel();
			jp2.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			int i=0;
			JLabel page = new JLabel((j+1)+"/"+num);
			i++;
			jp.setBackground(white);jp2.setBackground(white);
			jp.add(menu);add(jp);
			jp2.add(page);add(jp2);
		}
	}
	}
}
