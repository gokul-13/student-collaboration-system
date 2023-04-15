
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.StyledDocument;
import javax.swing.text.*;

import com.google.firebase.database.*;
import java.util.concurrent.BlockingQueue;

class SNote{
	String data;
	int num;
	
}
public class Note implements Runnable{
    private JButton jcomp1;
    private JButton jcomp2;
    private JTextPane jcomp3;
    private JTextField jcomp4;
    private JLabel jcomp5;
    JPanel panel;
    
	private BlockingQueue<String> recBuffer=null;

    public Note(BlockingQueue<String> rb) {panel = new JPanel();recBuffer =rb;}
    public void run() {
        jcomp1 = new JButton ("Add");
//        jcomp2 = new JButton ("Delete Note");
        jcomp3 = new  JTextPane();
        jcomp4 = new JTextField (5);
        jcomp5 = new JLabel ("Personal Notes :");

        jcomp3.setEnabled (false);
        jcomp3.setToolTipText ("Your notes");

        panel.setPreferredSize (new Dimension (752, 430));
        panel.setLayout (null);

        panel.add (jcomp1);
       // panel.add (jcomp2);
        panel.add (jcomp3);
        panel.add (jcomp4);
        panel.add (jcomp5);

        jcomp1.setBounds (595, 380, 100, 20);
       // jcomp2.setBounds (40, 375, 115, 35);
        jcomp3.setBounds (35, 50, 660, 310);
        jcomp4.setBounds (290, 375, 295, 30);
        jcomp5.setBounds (35, 15, 100, 25);
        
       jcomp1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String s = jcomp4.getText();
				recBuffer.put(s);}
				catch(Exception e2) {}
			}
		});
        JFrame frame = new JFrame ("Notes");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (panel);
        frame.pack();
        frame.setVisible (true);
		StyledDocument document= (StyledDocument) jcomp3.getDocument();
		Thread t = new Thread(new FillData(document,recBuffer));
		t.start();

    }


}