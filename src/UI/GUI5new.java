package UI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DB.Driver_connect;


public class GUI5new extends JFrame{
	private JButton zero = new JButton("0");
	private JPasswordField jt = new JPasswordField(21);
	
	public GUI5new() {
		int click = JOptionPane.showConfirmDialog(this, new manager(),"관리자 패스워드 입력",JOptionPane.OK_CANCEL_OPTION);
		
		if(click == JOptionPane.OK_OPTION) {
			
				String text = new String(jt.getPassword());
				
				
				if(text.equals("1234")) {
					new GUI6(); //관리 폼
				}else {
					JOptionPane.showConfirmDialog(this, "관리자 패스워드를 확인하십시오.","Message",JOptionPane.ERROR_MESSAGE,JOptionPane.YES_OPTION);
				
				}
			
		}
		
		setSize(300,300);
		setVisible(true);
	
	}
	class manager extends JPanel{
		public manager() {
			setLayout(new BorderLayout());
			
			add(new Northp(),BorderLayout.NORTH);
			add(new Centerp(),BorderLayout.CENTER);
			add(zero,BorderLayout.SOUTH);//0button
			zero.addActionListener(new actionmouse());
		}
	}
	class Northp extends JPanel{
		public Northp() {
			
			jt.setEnabled(false);
			add(jt);
			
		}
	}
	class Centerp extends JPanel{
		public Centerp() {
			setLayout(new GridLayout(3,3));
			JButton btn[]= new JButton[9];
			for(int i=0;i<9;i++) {
				String a = Integer.toString(i+1);
				btn[i] = new JButton(a);
				add(btn[i]);
				btn[i].addActionListener(new actionmouse());
			}
		}
	}
	class actionmouse implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton clickbtn = (JButton)e.getSource();
			String btnq = clickbtn.getText();
			String read;
			String text;
			
			read = new String(jt.getPassword());
			text = read+btnq;
			if(read.length()<4) {
				jt.setText(text);
			}
			
		}
	}

}
