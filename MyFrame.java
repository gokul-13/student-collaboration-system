import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import com.google.firebase.database.*;

public class MyFrame implements Runnable{
	
	private JPanel contentPane;
	private JTextField txtUserid;
	private JTextField txtTypeAMessage;
	private JTextPane textPane;
    private  String recName;
    private JButton btnShareAFile;
    private FireBaseService fbs;
	SimpleAttributeSet attributeSet;
	private String admin;
	StyledDocument document;
	Thread w;
	Thread rec;
	Thread send;
	boolean val=false;
    private BlockingQueue<String> sendBuffer=null;
    private BlockingQueue<String> recBuffer=null;
    private BlockingQueue<String> userBuffer=null;
    int length;
    SimpleAttributeSet rightAlign = new SimpleAttributeSet();
   User u;
    
    
	
	public MyFrame() {}
	public MyFrame(BlockingQueue<String> s,BlockingQueue<String> r,BlockingQueue<String> u,FireBaseService fb,User u1) {this.u=u1;this.sendBuffer=s;this.recBuffer=r;this.userBuffer=u;this.val=val;this.fbs=fb;this.admin=Variables.admin;}
	
	public void run() {
		StyleConstants.setAlignment(rightAlign,StyleConstants.ALIGN_RIGHT);
        StyleConstants.setItalic(rightAlign, true);  
        StyleConstants.setForeground(rightAlign, Color.blue);  
   	    
    
        
		 rec=new Thread(new ShowDbChanges(sendBuffer,recBuffer,userBuffer,fbs,u,1));
		 send=new Thread(new SendMessage(sendBuffer,fbs,u,1));
		 
		
		
		JFrame frame=new JFrame();
		//frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 677, 544);
		frame.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 181));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Welcome Home" +" "+ u.firstName +" "+u.lastName);
		lblNewLabel.setForeground(new Color(60, 179, 113));
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		JLabel lblNewLabell = new JLabel("Welcome Home" +" "+ u.firstName +" "+u.lastName);
		lblNewLabell.setForeground(new Color(60, 179, 113));
		lblNewLabell.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(72, 61, 139));
		
		JLabel lblNewLabel_1 = new JLabel("To:");
		lblNewLabel_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		
		txtUserid = new JTextField();
		txtUserid.setText("user_id");
		txtUserid.setColumns(10);
		
		JSeparator separator = new JSeparator();
		
		JButton btnNewButton = new JButton("Change User");
		btnNewButton.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				btnShareAFile.setEnabled(false);
				val=false;
				w.interrupt();
				rec.interrupt();
				send.interrupt();
				txtUserid.setEditable(true);
				textPane.setText("");
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(60, 179, 113));
		
		btnShareAFile = new JButton("Share a file");
		btnShareAFile.setEnabled(false);
		btnShareAFile.setForeground(new Color(255, 255, 255));
		btnShareAFile.setBackground(new Color(60, 179, 113));
		btnShareAFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sender = u.userName;
				String[] frcver = {recName};

				JFrame frame=new SendFile(sendBuffer,sender,frcver,document,u,fbs);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		
		JButton btnNewButton_1 = new JButton("Notes");
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setBackground(new Color(60, 179, 113));
		btnNewButton_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) { 
					
			   	 BlockingQueue<String> rec = new ArrayBlockingQueue<String>(1024);
				Thread t = new Thread(new Note(rec));
					
					t.start();
			}
		});
		
		JButton btnNewButton_2 = new JButton("Groups");
		btnNewButton_2.setForeground(new Color(255, 255, 255));
		btnNewButton_2.setBackground(new Color(60, 179, 113));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				
			   	 BlockingQueue<String> recBuffer = new ArrayBlockingQueue<String>(1024);
				   	 
			      
			   	 Thread t2=new Thread(new Groups(sendBuffer,recBuffer,fbs,u));
			       t2.start();	
			}
		});
		
		JButton btnNewButton_2_1 = new JButton("Join a Group");
		btnNewButton_2_1.setForeground(new Color(255, 255, 255));
		btnNewButton_2_1.setBackground(new Color(60, 179, 113));
		btnNewButton_2_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) { 
				Connection c = null;
				try{
					 Class.forName("org.postgresql.Driver");
			         c = DriverManager
			            .getConnection(Variables.host,
			            Variables.username, Variables.passkey);
			         
				Statement stmt = c.createStatement();
				//.out.println(" SIGNUP FIRSTING TO HOME ");
				JFrame frame1 = new JFrame();
			    String result = JOptionPane.showInputDialog(frame1, "Enter Group name:");
			    String sql = "INSERT INTO public.Groups(username, groupname)"
			       + "VALUES ('"+u.userName+"','"+result+"');";
			    stmt.executeUpdate(sql);
			     
				System.out.println(" Created");
				c.close();
				
				}
				catch(Exception eee) {
					System.out.println(" SIGNUP ERRORIGATING TO HOME ");

					System.out.println(eee);
				}
			}
		});
		
		
		
		JButton btnNewButton_3 = new JButton("LogOut");
		btnNewButton_3.setForeground(new Color(255, 255, 255));
		btnNewButton_3.setBackground(new Color(220, 20, 60));
		btnNewButton_3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				main_page second = new main_page();   
		        frame.setVisible(false); 
		        second.frame.setVisible(true);
		        
		        
			
			}
			});
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblNewLabel_2 = new JLabel("Chat Box");
		lblNewLabel_2.setForeground(new Color(46, 139, 87));
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		
		txtTypeAMessage = new JTextField();
		txtTypeAMessage.setText("Type a Message");
		txtTypeAMessage.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("Send");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
		        try {
		        	String message=txtTypeAMessage.getText();
				 //    document.insertString(document.getLength(),"\n"+admin+":-->"+message,rightAlign);
				     sendBuffer.put(message);
				}
			//	catch(BadLocationException ex) {}
		        catch(InterruptedException ex) {}
		        
			}
		});
		
		btnNewButton_4.setForeground(new Color(255, 255, 255));
		btnNewButton_4.setBackground(new Color(34, 139, 34));
		
		JLabel lblNewLabel_3 = new JLabel("Type a Message:");
		lblNewLabel_3.setForeground(new Color(199, 21, 133));
		lblNewLabel_3.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
	
		JButton btnNewButton_5 = new JButton("Lock User");
		btnNewButton_5.addActionListener(new ActionListener() {
		public synchronized void actionPerformed(ActionEvent e) {
				if(!val) {
					txtUserid.setEditable(false);
					btnShareAFile.setEnabled(true);
					try {
						
						recName=txtUserid.getText();
						sendBuffer.put(recName);
						userBuffer.put(recName);
						}catch(InterruptedException ex) {}
				val=true;
				rec=new Thread(new ShowDbChanges(sendBuffer,recBuffer,userBuffer,fbs,u,1
						));
				send=new Thread(new SendMessage(sendBuffer,fbs,u,1));
				w=new Thread(new FillData(document,recBuffer));
				w.start();
				rec.start();
				send.start();
				}
				else {
					val=false;
					w.interrupt();
					rec.interrupt();
					send.interrupt();
				}
				
				
			}
		});
		
		textPane = new JTextPane();
		JScrollPane spane=new JScrollPane(textPane);
		contentPane.add(spane);
		textPane.setEditable(false);
		  
		document= (StyledDocument) textPane.getDocument();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 601, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(100)
									.addComponent(btnNewButton_3))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(68)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(btnNewButton_2_1, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(btnNewButton_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(btnShareAFile, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
											.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(btnNewButton_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
							.addGap(27)
							.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(spane, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
							.addGap(61))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGap(155))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
							.addGap(246))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(txtUserid, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNewButton_5))
								.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE))
							.addGap(27))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_3)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txtTypeAMessage, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton_4))
							.addGap(50))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtUserid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)
						.addComponent(btnNewButton_5))
					.addGap(18)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_2)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(9)
									.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(19)
									.addComponent(spane, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnNewButton)
							.addGap(18)
							.addComponent(btnShareAFile)
							.addGap(18)
							.addComponent(btnNewButton_1)
							.addGap(18)
							.addComponent(btnNewButton_2)
							.addGap(18)
							.addComponent(btnNewButton_2_1)
							.addGap(18)
							.addComponent(btnNewButton_3)))
					.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtTypeAMessage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3))
					.addGap(18)
					.addComponent(btnNewButton_4)
					.addGap(20))
		);
		contentPane.setLayout(gl_contentPane);
		
	  
		
		
		
	}

}

/*
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class MyFrame implements Runnable{
	
	private JPanel contentPane;
	private JTextField txtUserid;
	private JTextField txtTypeAMessage;
	private JTextPane textPane;
    private  String recName;
    private FireBaseService fbs;
    User user;
	SimpleAttributeSet attributeSet;
	StyledDocument document;
	Thread w;
	Thread rec;
	Thread send;
	boolean val=false;
    private BlockingQueue<String> sendBuffer=null;
    private BlockingQueue<String> recBuffer=null;
    private BlockingQueue<String> userBuffer=null;
    private BlockingQueue<String> rcverBuffer=null;
    int length;
    SimpleAttributeSet rightAlign = new SimpleAttributeSet();
   
    
    
	
	public MyFrame() {}
	public MyFrame(BlockingQueue<String> s,BlockingQueue<String> r,BlockingQueue<String> u,FireBaseService fb,User u1) {this.user=u1;this.sendBuffer=s;this.recBuffer=r;this.userBuffer=u;this.val=val;this.fbs=fb;}
	
	public void run() {
		StyleConstants.setAlignment(rightAlign,StyleConstants.ALIGN_RIGHT);
        StyleConstants.setItalic(rightAlign, true);  
        StyleConstants.setForeground(rightAlign, Color.blue);  
   	    
    
        
		 rec=new Thread(new ShowDbChanges(sendBuffer,recBuffer,userBuffer,fbs,user));
		 send=new Thread(new SendMessage(rcverBuffer,sendBuffer,fbs,user));
		 
		
		 
		JFrame frame=new JFrame();
		//frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 677, 544);
		frame.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 181));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Welcome Home"+user.userName);
		lblNewLabel.setForeground(new Color(60, 179, 113));
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(72, 61, 139));
		
		JLabel lblNewLabel_1 = new JLabel("To:");
		lblNewLabel_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		
		txtUserid = new JTextField();
		txtUserid.setText("user_id");
		txtUserid.setColumns(10);
		
		JSeparator separator = new JSeparator();
		
		JButton btnNewButton = new JButton("Change User");
		btnNewButton.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				val=false;
				w.interrupt();
				rec.interrupt();
				send.interrupt();
				txtUserid.setEditable(true);
				textPane.setText("");
			}
		});
		JButton btnShareAFile = new JButton("Share a file");
		btnShareAFile.setForeground(new Color(255, 255, 255));
		btnShareAFile.setBackground(new Color(60, 179, 113));
		btnShareAFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame=new SendFile(sendBuffer);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(60, 179, 113));
		
	
		
		JButton btnNewButton_1 = new JButton("Send a Voice Message");
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setBackground(new Color(60, 179, 113));
		
		JButton btnNewButton_2 = new JButton("Create a Group");
		btnNewButton_2.setForeground(new Color(255, 255, 255));
		btnNewButton_2.setBackground(new Color(60, 179, 113));
		
		JButton btnNewButton_2_1 = new JButton("Join a Group");
		btnNewButton_2_1.setForeground(new Color(255, 255, 255));
		btnNewButton_2_1.setBackground(new Color(60, 179, 113));
		
		JButton btnNewButton_3 = new JButton("LogOut");
		btnNewButton_3.setForeground(new Color(255, 255, 255));
		btnNewButton_3.setBackground(new Color(220, 20, 60));
	
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblNewLabel_2 = new JLabel("Chat Box");
		lblNewLabel_2.setForeground(new Color(46, 139, 87));
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		
		txtTypeAMessage = new JTextField();
		txtTypeAMessage.setText("Type a Message");
		txtTypeAMessage.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("Send");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
		        try {
		        	String message=txtTypeAMessage.getText();
				    // document.insertString(document.getLength(),"\n"+user.userName+":-->"+message,rightAlign);
				     sendBuffer.put(message);
				     
				     
				}
			//	catch(BadLocationException ex) {}
		        catch(InterruptedException ex) {}
		        
			}
		});
		btnNewButton_4.setForeground(new Color(255, 255, 255));
		btnNewButton_4.setBackground(new Color(34, 139, 34));
		
		JLabel lblNewLabel_3 = new JLabel("Type a Message:");
		lblNewLabel_3.setForeground(new Color(199, 21, 133));
		lblNewLabel_3.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
	
		JButton btnNewButton_5 = new JButton("Lock User");
		btnNewButton_5.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				
				
				if(!val) {
					txtUserid.setEditable(false);
					try {
						
						recName=txtUserid.getText();
						sendBuffer.put(recName);
						userBuffer.put(recName);
						}catch(InterruptedException ex) {}
				val=true;
				rec=new Thread(new ShowDbChanges(sendBuffer,recBuffer,userBuffer,fbs,user));
				send=new Thread(new SendMessage(rcverBuffer,sendBuffer,fbs,user));
				w=new Thread(new FillData(document,recBuffer));
				w.start();
				rec.start();
				send.start();
				}
				else {
					val=false;
					w.interrupt();
					rec.interrupt();
					send.interrupt();
				}
				
				
			}
		});
		
		textPane = new JTextPane();
		JScrollPane spane=new JScrollPane(textPane);
		contentPane.add(spane);
		textPane.setEditable(false);
		  
   	    
		
		
		
		document= (StyledDocument) textPane.getDocument();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 601, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(100)
									.addComponent(btnNewButton_3))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(68)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(btnNewButton_2_1, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(btnNewButton_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(btnShareAFile, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
											.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(btnNewButton_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
							.addGap(27)
							.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
							.addGap(61))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGap(155))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
							.addGap(246))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(txtUserid, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNewButton_5))
								.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE))
							.addGap(27))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_3)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txtTypeAMessage, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton_4))
							.addGap(50))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtUserid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)
						.addComponent(btnNewButton_5))
					.addGap(18)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_2)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(9)
									.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(19)
									.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnNewButton)
							.addGap(18)
							.addComponent(btnShareAFile)
							.addGap(18)
							.addComponent(btnNewButton_1)
							.addGap(18)
							.addComponent(btnNewButton_2)
							.addGap(18)
							.addComponent(btnNewButton_2_1)
							.addGap(18)
							.addComponent(btnNewButton_3)))
					.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtTypeAMessage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3))
					.addGap(18)
					.addComponent(btnNewButton_4)
					.addGap(20))
		);
		contentPane.setLayout(gl_contentPane);
		
	  
		
		
		
	}

}
*/