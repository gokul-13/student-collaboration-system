
import java.awt.Color;
import java.util.concurrent.BlockingQueue;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class FillData implements Runnable {
	StyledDocument document;
	private BlockingQueue<String> recBuffer=null;

	public FillData(StyledDocument d,BlockingQueue<String> r) {this.document=d;this.recBuffer=r;}
	
	public void run() {
		
		 SimpleAttributeSet leftAlign = new SimpleAttributeSet();
	    StyleConstants.setItalic(leftAlign, true);  
        StyleConstants.setForeground(leftAlign, Color.BLACK);
		
		while(true) {
			try {
				
			document.insertString(document.getLength(),"\n"+recBuffer.take(),leftAlign);
			}catch(InterruptedException ex) {return;}
			catch(BadLocationException ex) {}
			}
		
		
		
	}

}
