package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		String sql = "update meal set price="+com2.getSelectedItem()+",maxCount="+com3.getSelectedItem()+" where mealName='"+jf.getText()+"'";
		
		try {
			Connection con = Driver_connect.makeConnection("meal");
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(null, "메뉴가 정상적으로 수정되었습니다.","Message",JOptionPane.QUESTION_MESSAGE);
			dispose();
		}catch(SQLException e1) {
			System.out.println("업테이트 sql 오류");
		}
		
		
	}
}
