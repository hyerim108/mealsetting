package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI6 extends JFrame{
	private JButton btn[] = new JButton[5];
	private String text[] = {"메뉴 등록" ,"메뉴 관리","결제 조회","종류별 차트","종료"};
	public GUI6() {
		setTitle("관리");
		Container c = getContentPane();
		
		c.add(new NorthP(),BorderLayout.NORTH);
		c.add(new CenterP(),BorderLayout.CENTER);
		
		setSize(500,450);
		setVisible(true);
	}
	class NorthP extends JPanel{
		public NorthP() {
			for(int i =0;i<text.length;i++) {
				btn[i]= new JButton(text[i]);
				add(btn[i]);
			}
			btn[0].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					new GUI7(); //메뉴등록
				}
			});
			btn[1].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//메뉴관리
					new GUI8new();
				}
			});
			btn[2].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//결제 조회
					new GUI9new();
				}
			});
			btn[3].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//종류별 차트
					new GUI10();
				}
			});
			btn[4].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//종료
					dispose();
				}
			});
		}
	}
	class CenterP extends JPanel{
		public CenterP() {
			ImageIcon img = new ImageIcon(".\\DataFiles\\main.jpg");
			JLabel l = new JLabel(img);
			add(l);
		}
	}
	
//	public static void main(String args[]) {
//		new GUI6();
//	}
}
