import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.*;

import javax.swing.border.EmptyBorder;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import javax.swing.GroupLayout.Alignment;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.Color;
import java.awt.Font;
import com.google.firebase.database.*;
import java.util.*;
public class Groups implements Runnable{

	private BlockingQueue<String> sendBuffer=null;
	private BlockingQueue<String> recBuffer=null;
	private BlockingQueue<String> userBuffer=null;
	private FireBaseService fbs = null;
	private User u;
	private JPanel contentPane;
	private JTextField txtEnterYourMessage;
    private JFrame frame;
    private JComboBox comboBox;
    private String g;
    Thread rec,send,w;
    StyledDocument document;
    
    public Groups(BlockingQueue<String> s,BlockingQueue<String> r,FireBaseService fb,User u1) {this.sendBuffer=s;this.u=User.getUser(u1.userName);this.recBuffer=r;this.fbs=fb;}

			JLabel lblNewLabel;
			JButton btnNewButton,btnNewButton_1;
			JTextPane textPane;
			JButton btnNewButton_3, btnNewButton_4, btnNewButton_7, btnNewButton_8, btnNewButton_8_1, btnNewButton_8_2;
	public void run() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 800, 800);
		frame.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 192, 203));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		
		comboBox = new JComboBox();
		comboBox.setBackground(new Color(255, 0, 102));
		comboBox.setForeground(new Color(255, 255, 255));
		Object a[]=null;
		try {
		a= u.groups.toArray() ;
		}
		catch(Exception e) {System.out.println(u.groups);}
		comboBox.setModel(new DefaultComboBoxModel(a));
		
		
		
	    lblNewLabel = new JLabel(u.userName+", Rock with your Groups!");
		lblNewLabel.setFont(new Font("Orator Std", Font.BOLD, 16));
		lblNewLabel.setForeground(new Color(255, 0, 153));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnNewButton = new JButton("Lock Group");
		btnNewButton.setFont(new Font("Source Sans Pro Black", Font.BOLD, 10));
		btnNewButton.setBackground(new Color(255, 0, 102));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				try {
					comboBox.setEditable(false);
					btnNewButton.setEnabled(false);
					btnNewButton_1.setEnabled(true);
					String recName=(String)comboBox.getSelectedItem();
					sendBuffer.put(recName);
					g=recName;
					
					rec=new Thread(new ShowDbChanges(sendBuffer,recBuffer,userBuffer,fbs,u,g,2));
					send=new Thread(new SendMessage(sendBuffer,fbs,u,g,2));
					w=new Thread(new FillData(document,recBuffer));
					w.start();
					rec.start();
					send.start();
					}
			catch(Exception ex) {System.out.println("Hi");}
			}
					/*else {
						val=false;
						w.interrupt();
						rec.interrupt();
						send.interrupt();
					}*/
					
				}
			);
		
		 btnNewButton_1 = new JButton("Change Group");
		btnNewButton_1.setFont(new Font("Source Code Pro", Font.BOLD, 10));
		btnNewButton_1.setBackground(new Color(255, 0, 102));
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					comboBox.setEditable(true);
					btnNewButton.setEnabled(true);
					btnNewButton_1.setEnabled(false);
					
					}
			catch(Exception ex) {System.out.println("Hello");}
			}
			
		});
		
	textPane = new JTextPane();
	JScrollPane spane=new JScrollPane(textPane);
	contentPane.add(spane);
	textPane.setEditable(false);
	  
	document= (StyledDocument) textPane.getDocument();
		
	btnNewButton_3 = new JButton("Add Participant");
		btnNewButton_3.setBackground(new Color(255, 0, 102));
		btnNewButton_3.setForeground(new Color(255, 255, 255));
		btnNewButton_3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) { 
				Connection c = null;
				try{
					 Class.forName("org.postgresql.Driver");
			         c = DriverManager
			            .getConnection(Variables.host,
			            Variables.username, Variables.passkey);
			         
				Statement stmt = c.createStatement();
				System.out.println(" SIGNUP FIRSTING TO HOME ");
				JFrame frame1 = new JFrame();
			    String result = JOptionPane.showInputDialog(frame1, "Enter printer name:");
			    String sql = "INSERT INTO public.Groups(username, groupname)"
			       + "VALUES ('"+result+"','"+g+"');";
			    stmt.executeUpdate(sql);
			     
				System.out.println(" SIGNUP COMPLETE....NAVIGATING TO HOME ");
				
				 c.close();
				}
				catch(Exception eee) {
					System.out.println(" SIGNUP ERRORIGATING TO HOME ");

					System.out.println(eee);
				}
			}
		});
		
	 btnNewButton_4 = new JButton("Send File");
		btnNewButton_4.setBackground(new Color(255, 0, 102));
		btnNewButton_4.setForeground(new Color(255, 255, 255));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sender = g;
				Connection c = null;
				try{
					 Class.forName("org.postgresql.Driver");
			         c = DriverManager
			            .getConnection(Variables.host,
			            Variables.username, Variables.passkey);
				Statement stmt = c.createStatement();
				
				String sql = "SELECT * FROM groups WHERE groupname = '" + g +"';";


				   ResultSet resultSet = stmt.executeQuery(sql);
				   String t;
				   ArrayList<String> arr = new ArrayList<>();
				        while (resultSet.next()) {
				        	t=resultSet.getString("username");	        
				        	arr.add(t);
				        	System.out.println(t);
				        }
				        c.close();
						    
							
				
			    
			    
			     
				System.out.println(" \nUSERS Fetched\n ");
				 c.close();
				
				String[] frcver = new String[arr.size()];
				int i=0;
				for(String a:arr) {
					frcver[i++]=a;
				}

				JFrame frame=new SendFile(sendBuffer,sender,frcver,document,u,fbs);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}catch(Exception e4) {e4.printStackTrace();}
				}
		});
		
		
		btnNewButton_7 = new JButton("Show Participants");
		btnNewButton_7.setBackground(new Color(255, 0, 102));
		btnNewButton_7.setForeground(new Color(255, 255, 255));
		
		btnNewButton_8 = new JButton("Exit Group");
		btnNewButton_8.setBackground(new Color(255, 0, 102));
		btnNewButton_8.setForeground(new Color(255, 255, 255));
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				Connection c = null;
				try{
					 Class.forName("org.postgresql.Driver");
			         c = DriverManager
			            .getConnection(Variables.host,
			            Variables.username, Variables.passkey);
				Statement stmt = c.createStatement();
				JFrame frame1 = new JFrame();
			    String result = JOptionPane.showInputDialog(frame1, "Confirm?");
			    if(result.equals("YES")) {
			    	String sql = "delete from groups where username='"+u.userName+"' and groupname='"+g+"';";
						    stmt.executeUpdate(sql);
						    System.out.println("OVER");
							JOptionPane.showMessageDialog(null,"Exited Group!Restart Window!");
							return;

			    }
			    
			     
				System.out.println(" SIGNUP COMPLETE... NAVIGATING TO HOME ");
				 c.close();
				
				}
				catch(Exception eee) {
					System.out.println(" SIGNUP ERRORIGATING TO HOME ");

					System.out.println(eee);
				}
			}});		
			
		
		
		txtEnterYourMessage = new JTextField();
		txtEnterYourMessage.setText("Enter your Message");
		txtEnterYourMessage.setColumns(10);
		
		btnNewButton_8_1 = new JButton("Send");
		btnNewButton_8_1.setForeground(Color.WHITE);
		btnNewButton_8_1.setBackground(new Color(255, 0, 102));
		btnNewButton_8_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
		        try {
		        	String message=txtEnterYourMessage.getText();
				 //    document.insertString(document.getLength(),"\n"+admin+":-->"+message,rightAlign);
				     sendBuffer.put(message);
				}
			//	catch(BadLocationException ex) {}
		        catch(InterruptedException ex) {}
		        
			}
		});
		
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(237, Short.MAX_VALUE)
					.addComponent(lblNewLabel)
					.addGap(193))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
								.addComponent(btnNewButton_8, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
								.addComponent(btnNewButton_4, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
								//.addComponent(btnNewButton_5, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
								//.addComponent(btnNewButton_6, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
								.addComponent(btnNewButton_7, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
								.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
							.addGap(18))
						.addGroup(gl_contentPane.createSequentialGroup()
							//.addComponent(btnNewButton_8_2, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(comboBox, 0, 318, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGap(17)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(txtEnterYourMessage, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnNewButton_8_1, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
								.addComponent(spane, GroupLayout.PREFERRED_SIZE, 459, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(12)
					.addComponent(lblNewLabel)
					.addGap(8)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 0, Short.MAX_VALUE)
							.addComponent(spane, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(54)
							.addComponent(btnNewButton_7, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							//.addComponent(btnNewButton_6, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
							//.addPreferredGap(ComponentPlacement.UNRELATED)
							//.addComponent(btnNewButton_5, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
							//.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNewButton_4, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
							.addGap(47)
							.addComponent(btnNewButton_8, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
							.addGap(45)
							//.addComponent(btnNewButton_8_2, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						//	.addGap(180)
							)
						)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtEnterYourMessage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_8_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(19))
		);
		contentPane.setLayout(gl_contentPane);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
