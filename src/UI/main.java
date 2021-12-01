package UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
//사용자 관리자 등등
public class main extends JFrame{
	private String btnName[] = {"사용자","관리자","사원등록","종료"}; // btn이름넣는
	private JButton btn[]=new JButton[4];
	public main(){
		setTitle("메인");
		Container c =getContentPane();
		setLayout(new GridLayout(4,1));
		for(int i=0;i<btnName.length;i++) {
			btn[i]=new JButton(btnName[i]);
			c.add(btn[i]);
			btn[i].setBackground(Color.GRAY);
			btn[i].setForeground(Color.white);
	
		}
		btn[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new GUI2(); //식권 발매 프로그램폼으로
				dispose();
			}
			});
		btn[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new GUI5();
				dispose();
			}
		});
		btn[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new GUI11();
				dispose();
			}
		});
		btn[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		setSize(300,300);
		setVisible(true);
	}
	public static void main(String args[]) {
		new main();
	}
}
