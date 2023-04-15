import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledDocument;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;

public class SendFile extends JFrame {

	private JPanel contentPane;
	private SendFile dummy;
	private JTextField textField;
	private String filePath;
	private JButton btnNewButton_1;
	private BlockingQueue<String> sendBuffer;
	private String sender;
	private String[] frcver;
	User u;
	FireBaseService fbs;
	StyledDocument document;

	public SendFile(BlockingQueue<String> ut,String sender,String[] frcver,StyledDocument d,User u1,FireBaseService fbs) {
		this.document=d;
		this.frcver=frcver;
		this.sender=sender;
		this.sendBuffer=ut;
		this.fbs=fbs;
		this.u=u1;
		setResizable(false);
		dummy=this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 206);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		String s = (frcver.length==1)?frcver[0]:sender;
		JLabel lblNewLabel = new JLabel("To User: "+ s);
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Source Code Pro Semibold", Font.BOLD, 15));
		
		JButton btnNewButton = new JButton("Choose File");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(60, 179, 113));
		btnNewButton.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
				JFileChooser fc=new JFileChooser(); 
				int i=fc.showOpenDialog(dummy);
				if(i==JFileChooser.APPROVE_OPTION) {
					File f=fc.getSelectedFile();
					textField.setText(f.getAbsolutePath());
					filePath=f.getAbsolutePath();
					btnNewButton_1.setEnabled(true);
					System.out.println(f.getAbsolutePath());
				}
			}
		});
		btnNewButton.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
			}
		});
		
		textField = new JTextField();
		textField.setBackground(new Color(60, 179, 113));
		textField.setForeground(new Color(255, 255, 255));
		textField.setColumns(10);
		textField.setEditable(false);
		
	    btnNewButton_1 = new JButton("Send");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sending started");
				
				Thread t=new Thread(new InitTransfer(filePath,sendBuffer,sender,frcver,document,fbs));
				
				t.start();
			}
		});
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setBackground(new Color(60, 179, 113));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(75, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)
							.addGap(86))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(textField, Alignment.LEADING)
								.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE))
							.addGap(58))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnNewButton_1)
							.addGap(232))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton_1)
					.addContainerGap(194, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}

/*
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;

public class SendFile extends JFrame {

	private JPanel contentPane;
	private SendFile dummy;
	private JTextField textField;
	private String filePath;
	private JButton btnNewButton_1;
	private BlockingQueue<String> sendBuffer;
	

	public SendFile(BlockingQueue<String> ut) {
		this.sendBuffer=ut;
		setResizable(false);
		dummy=this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 206);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("To User:");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Source Code Pro Semibold", Font.BOLD, 15));
		
		JButton btnNewButton = new JButton("Choose File");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(60, 179, 113));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc=new JFileChooser(); 
				int i=fc.showOpenDialog(dummy);
				if(i==JFileChooser.APPROVE_OPTION) {
					File f=fc.getSelectedFile();
					textField.setText(f.getAbsolutePath());
					filePath=f.getAbsolutePath();
					btnNewButton_1.setEnabled(true);
					System.out.println(f.getAbsolutePath());
				}
			}
		});
		btnNewButton.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
			}
		});
		
		textField = new JTextField();
		textField.setBackground(new Color(60, 179, 113));
		textField.setForeground(new Color(255, 255, 255));
		textField.setColumns(10);
		textField.setEditable(false);
		
	    btnNewButton_1 = new JButton("Send");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread t=new Thread(new InitTransfer(filePath,sendBuffer));
				t.run();
			}
		});
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setBackground(new Color(60, 179, 113));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(75, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)
							.addGap(86))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(textField, Alignment.LEADING)
								.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE))
							.addGap(58))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnNewButton_1)
							.addGap(232))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton_1)
					.addContainerGap(194, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
*/