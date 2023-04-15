 import java.awt.EventQueue;
 import com.google.firebase.database.*;
 import java.io.IOException;
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.*;
 import com.google.firebase.database.*;
 import java.io.IOException;
 import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;

public class main_page implements Runnable {

	 JFrame frame;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JButton btnNewButton;
	private JLabel lblNewLabel_3;
	private JButton btnNewButton_1;
	private JLabel lblNewLabel_4;
	main_page w;
	User u=new User();
	String username;String password;
	boolean login=false;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
	//	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main_page window = new main_page();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		//});
	//}

	/**
	 * Create the application.
	 */
	
	public main_page() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 222, 173));
		frame.setBounds(100, 100, 452, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lblNewLabel = new JLabel("Welcome to StuDGether ,  Have Fun");
		lblNewLabel.setForeground(new Color(220, 20, 60));
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblNewLabel_1 = new JLabel("Enter User_id:");
		lblNewLabel_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		
		lblNewLabel_2 = new JLabel("Enter Password:");
		lblNewLabel_2.setForeground(new Color(138, 43, 226));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		
		txtUsername = new JTextField();
		//txtUsername.setText("UserName");
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		//txtPassword.setText("Password");
		txtPassword.setColumns(10);
		
		
		btnNewButton = new JButton("Login");
		btnNewButton.setBackground(new Color(245, 255, 250));
		btnNewButton.setForeground(new Color(255, 0, 0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection c = null;
					Class.forName("org.postgresql.Driver");
			         c = DriverManager
			            .getConnection(Variables.host,
			            Variables.username, Variables.passkey);
			         
				Statement stmt = c.createStatement();
				username = txtUsername.getText();
				password = txtPassword.getText();
			   
				u=User.getUser(username);
			      
			            if(u.password.equals(password)) {
			            	System.out.println("LOGED IN!!!!"+u.groups);
			            	login=true;
			        }  
			            else {
			            	System.out.println("RETRY!!!!");
			            	txtUsername.setText("UserName");
			            	
			            	
			            }
			        
			            c.close();
			        }
				catch (SQLException ex) {  
			    	System.out.println("RETRY3!!!!"+ex);

			    }
				catch (Exception ee) {
			    	System.out.println("RETRY4!!!!"+ee);

				}
				
				System.out.println(" LOGIN COMPLETE ");
				
				
				if(!login) {
				main_page second = new main_page();   
		        frame.setVisible(false); 
		        second.frame.setVisible(true);
				}else {
					//Home frame1 = new Home();
				//	frame1.setVisible(true);
					frame.setVisible(false); 
					String val="false";
				   	 BlockingQueue<String> sendBuffer = new ArrayBlockingQueue<String>(1024);
				   	 BlockingQueue<String> recBuffer = new ArrayBlockingQueue<String>(1024);
				   	 BlockingQueue<String> userBuffer=new ArrayBlockingQueue<String>(1024);
				   	FireBaseService fbs = null;
				    try {
				            fbs = new FireBaseService();
				        } 
				    catch (IOException e3) {
				            e3.printStackTrace();
				        }
				      
				       Thread t2=new Thread(new MyFrame(sendBuffer,recBuffer,userBuffer,fbs,u));
				       t2.start();
				}
				
			}
		});
		
		lblNewLabel_3 = new JLabel("Not yet a user !?");
		lblNewLabel_3.setForeground(new Color(30, 144, 255));
		lblNewLabel_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnNewButton_1 = new JButton("SignUp");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp ss = new SignUp();
				
				Thread t=new Thread(ss);
		        t.start();
				frame.setVisible(false);
				
				
			}
		});
		btnNewButton_1.setBackground(new Color(255, 0, 0));
		btnNewButton_1.setForeground(new Color(245, 255, 250));
		
		lblNewLabel_4 = new JLabel("Join the Party!");
		lblNewLabel_4.setForeground(new Color(255, 20, 147));
		lblNewLabel_4.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(39)
							.addComponent(lblNewLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(34)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblNewLabel_2)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(btnNewButton)
									.addGap(65)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(btnNewButton_1)
									.addGap(12))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
					.addContainerGap(42, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(44)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				 		.addComponent(btnNewButton_1))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_4)
						.addComponent(btnNewButton))
					.addContainerGap(98, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
