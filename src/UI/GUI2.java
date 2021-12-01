package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
//테이블 양식중식 선택 

public class GUI2 extends JFrame{
	private JButton btn[] = new JButton[4];//음식 사진 버튼 4개
	private String img[] = {"menu_1","menu_2","menu_3","menu_4"};
	private ImageIcon imgcon[]=new ImageIcon[4];
	private String too[] = {"한식","중식","일식","양식"};

	public GUI2() {
		setTitle("식권 발매 프로그램");
		
		Container c =getContentPane();
		c.add(new NorthPanel(),BorderLayout.NORTH);//식권발매 제복
		c.add(new CenterPanel(),BorderLayout.CENTER);//탭안에 메뉴
		c.add(new SouthPanel(),BorderLayout.SOUTH);// south 현재시간
		
		setVisible(true);
		setSize(490,780);
	}
	class Image extends JPanel{ //센터패널에 넣을 버튼이미지
		public Image() {
			setLayout(new GridLayout(2,2));
			for(int i=0; i<img.length;i++) {
				imgcon[i] = new ImageIcon(".\\DataFiles\\"+img[i]+".png");	
				btn[i]=new JButton(imgcon[i]);
				btn[i].setBackground(Color.WHITE);
				btn[i].setToolTipText(too[i]);
				btn[i].addActionListener(new mymouse());
				add(btn[i]);
			}
		}
	}
	class mymouse implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b= (JButton)e.getSource();
			if(b==btn[0]) {
				new GUI3new("1");
			}else if(b==btn[1]){
				new GUI3("2");
			}else if(b==btn[2]){
				new GUI3("3");
			}else if(b==btn[3]){
				new GUI3("4");
			}
		}
	}
		
	class NorthPanel extends JPanel{
		public NorthPanel() {
			setBackground(Color.WHITE);
			JLabel la = new JLabel("식권 발매 프로그램");
			la.setFont(new Font("고딕",Font.BOLD,30));
			add(la);
		}
	}
	class CenterPanel extends JPanel{
		public CenterPanel() {
			setBackground(Color.WHITE);
			JTabbedPane tab = new JTabbedPane();
			tab.addTab("메뉴", new Image());
			add(tab);
		}
	}

	class SouthPanel extends JPanel{
		JLabel la = new JLabel();
		public SouthPanel() {
			add(la);
			setBackground(Color.BLACK);
			ThreadTime th = new ThreadTime();
			th.start();
		}
		class ThreadTime extends Thread{
			
			public void run() {
				int i =0;
				while(true) {
					
//						i++;
//						System.out.println("현재시간 : "+disp);
					try {
						Thread.sleep(1000);
					}catch(Exception ex) {}
					
					Calendar c = Calendar.getInstance();
					int year = c.get(Calendar.YEAR);
					int month = c.get(Calendar.MONTH)+1;
					int day = c.get(Calendar.DAY_OF_MONTH);
					int hour = c.get(Calendar.HOUR_OF_DAY);
					int min = c.get(Calendar.MINUTE);
					int second = c.get(Calendar.SECOND);
					String hap = "현재시간 : "+year+"년 "+month+"월 "+day+"일 "+hour+"시 "+min+"분 "+second+"초";
					la.setText(hap);
					la.setForeground(Color.WHITE);
					la.setBackground(Color.BLACK);
					la.setFont(new Font("Italic",Font.ITALIC,20));
					la.setOpaque(true);
				}
			}
		}
		
		
	}
	public static void main(String arg[]) {
		new GUI2();
	}
}
