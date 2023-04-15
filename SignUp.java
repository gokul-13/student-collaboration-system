import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class SignUp implements Runnable {

	private JPanel contentPane;
	private JTextField txtFirstname;
	private JTextField txtLastname;
	private JTextField txtUserid;
	private JTextField txtPassword;
	private JTextField txtConfirmPassword;
	private JTextField txtPhoneNo;
	private JTextField txtEMail;
	User u1;
	boolean b=false;
	/**
	 * Launch the application.
	 */
	//public static void main(String[] args) {
		//EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
					frame.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	//	});
	//}

	
JFrame frame ;
JLabel lblNewLabel ;
JButton btnNewButton ;
JLabel lblNewLabel_1,lblNewLabel_1_1,lblNewLabel_1_1_1,lblNewLabel_1_1_1_1,lblNewLabel_1_1_1_1_2,lblNewLabel_1_1_1_1_2_1,lblNewLabel_1_1_1_1_1;
	public SignUp() {
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 222, 173));
		frame.setBounds(100, 100, 501, 378);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lblNewLabel = new JLabel("SignUp");
		lblNewLabel.setForeground(new Color(148, 0, 211));
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtFirstname = new JTextField();
		txtFirstname.setText("FirstName");
		txtFirstname.setColumns(10);
		
		txtLastname = new JTextField();
		txtLastname.setText("LastName");
		txtLastname.setColumns(10);
		
		txtUserid = new JTextField();
		txtUserid.setText("User_id");
		txtUserid.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setText("Password");
		txtPassword.setColumns(10);
		
		txtConfirmPassword = new JTextField();
		txtConfirmPassword.setText("Confirm Password");
		txtConfirmPassword.setColumns(10);
		
		txtPhoneNo = new JTextField();
		txtPhoneNo.setText("Phone No");
		txtPhoneNo.setColumns(10);
		
		txtEMail = new JTextField();
		txtEMail.setText("E mail");
		txtEMail.setColumns(10);
		
		
		btnNewButton = new JButton("Sign_Up");
		btnNewButton.setBackground(new Color(248, 248, 255));
		btnNewButton.setForeground(new Color(148, 0, 211));
		btnNewButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) { 
				
		Connection c = null;
		
		String userName="",firstName="",lastName="",mobile="",email="",password="k",cnfpass="l";      
		try {
				b=false;
				if(!b) {
				userName = txtUserid.getText();
					firstName = txtFirstname.getText();
					lastName = txtLastname.getText();
					mobile = txtPhoneNo.getText();
					email = txtEMail.getText();
					password = txtPassword.getText();
					cnfpass = txtConfirmPassword.getText();
					
					if(password.equals(cnfpass)) {
					u1 = new User(userName,firstName,lastName,mobile,email,password);
					b=true;
					System.out.println("PASSWORD MATCHES!!!");
					}
					else {
						txtConfirmPassword.setText("ENTER NEW PASSWORD");
						txtPassword.setText("RETRY");
						JOptionPane.showMessageDialog(null,"Passwords do not match!");
					}
				}
				
				System.out.println("ADDING TO DB!!!"+b);
			
					}
					catch(Exception ee) {
						System.out.println("RETRY INPUT!!");
						b=false;
					}
			if(b){	
				b=false;
				try{
					Class.forName("org.postgresql.Driver");
			         c = DriverManager
			            .getConnection(Variables.host,
			            Variables.username, Variables.passkey);
				Statement stmt = c.createStatement();
				System.out.println(" SIGNUP FIRSTING TO HOME ");

			    String sql = "INSERT INTO user_details (userName,firstName,lastName,mobile,email,password) "
			       + "VALUES ('"+userName+"','"+firstName+"','"+lastName+"','"+mobile+"','"+email+"','"+password+"');";
			    stmt.executeUpdate(sql);
			    
				
				
				System.out.println(" SIGNUP COMPLETE....NAVIGATING TO HOME ");
				b=true;
				JOptionPane.showMessageDialog(null,"Sign Up Complete!");
				c.close();
				}
				catch(Exception eee) {
					System.out.println(" SIGNUP ERRORIGATING TO HOME ");

					System.out.println(eee);
					b=false;
				}
			}
			if(b){
				
				////Home frame1 = new Home();
			//	frame1.setVisible(true);
				frame.setVisible(false);
				System.out.println(" FRame ENDED ");
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
			      
			       Thread t2=new Thread(new MyFrame(sendBuffer,recBuffer,userBuffer,fbs,u1));
			       t2.start();
				

				
			}
		
			/*else {
					SignUp second = new SignUp();   
			       setVisible(false); 
			        second.setVisible(true);
				}*/
			}
		});
		
	 lblNewLabel_1 = new JLabel("Enter First Name");
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		lblNewLabel_1.setForeground(new Color(138, 43, 226));
		
		 lblNewLabel_1_1 = new JLabel("Enter Last Name");
		lblNewLabel_1_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_1_1.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		
		 lblNewLabel_1_1_1 = new JLabel("Enter Unique User_id");
		lblNewLabel_1_1_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_1_1_1.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		
		 lblNewLabel_1_1_1_1 = new JLabel("Enter Password");
		lblNewLabel_1_1_1_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_1_1_1_1.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		
		 lblNewLabel_1_1_1_1_1 = new JLabel("Confirm Password");
		lblNewLabel_1_1_1_1_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_1_1_1_1_1.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		
		 lblNewLabel_1_1_1_1_2 = new JLabel("Enter Phone No");
		lblNewLabel_1_1_1_1_2.setForeground(new Color(138, 43, 226));
		lblNewLabel_1_1_1_1_2.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		
		 lblNewLabel_1_1_1_1_2_1 = new JLabel("Enter Email");
		lblNewLabel_1_1_1_1_2_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_1_1_1_1_2_1.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		
		JLabel lblNewLabel_2 = new JLabel("Already a User !?");
		lblNewLabel_2.setForeground(new Color(255, 69, 0));
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		
		JButton btnNewButton_1 = new JButton("Login");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main_page l= new main_page();
				Thread t1=new Thread(l);
	        t1.start();
	        frame.setVisible(false);
			}
		});
		btnNewButton_1.setBackground(new Color(230, 230, 250));
		btnNewButton_1.setForeground(new Color(106, 90, 205));
		GroupLayout gl_contentPane = new GroupLayout(frame.getContentPane());
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(49)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1_1_1_1_1, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtConfirmPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1_1_1_1, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1_1_1)
							.addGap(18)
							.addComponent(txtUserid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtLastname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addGap(18)
							.addComponent(txtFirstname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1_1_1_1_2_1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtEMail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1_1_1_1_2, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtPhoneNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addGap(21))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnNewButton_1)
							.addGap(60))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(194)
					.addComponent(btnNewButton)
					.addContainerGap(222, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtFirstname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtLastname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtUserid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1_1_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1_1_1_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtConfirmPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1_1_1_1_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtPhoneNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1_1_1_1_2, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtEMail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1_1_1_1_2_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(btnNewButton))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(78)
							.addComponent(lblNewLabel_2)
							.addGap(18)
							.addComponent(btnNewButton_1)))
					.addContainerGap(44, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(gl_contentPane);
	}

}
