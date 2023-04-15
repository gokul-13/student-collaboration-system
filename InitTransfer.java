

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import com.google.firebase.database.*;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class InitTransfer implements Runnable {
	private BlockingQueue<String> sendBuffer;
	InetAddress id;
	Socket sc;
	private String sender;
	private String[] frcver;
	private String filePath;
	private StyledDocument document;
	FireBaseService fbs;
	public InitTransfer(String path,BlockingQueue<String> q,String sender,String[] rec,StyledDocument document,FireBaseService fbs) {this.fbs=fbs;this.filePath=path;this.sendBuffer=q;this.sender=sender;this.frcver=rec;this.document=document;}
	public void run() {
	try {
		 id=InetAddress.getByName(Variables.ip);
	} catch (UnknownHostException e) {
		e.printStackTrace();
	}
	
	try {
		System.out.println("123");
	    sc=new Socket(id,99);
		System.out.println("234432222");

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	int len=filePath.lastIndexOf('.');
	int point=filePath.lastIndexOf('\\');
	if(len==-1) return;
	else {
		
			int ret=sendImage(filePath);
			if(ret==-1) {
				JOptionPane.showMessageDialog(null,"Technical Error! File Send Failed");
			}
			else {
				JOptionPane.showMessageDialog(null,"File Sent");
			}
		
	}
	try {
		System.out.println("Sending started222");

		if(point!=-1) {
			for(String a:frcver) {
				String path="/File/"+sender+"/Receive/"+a;
				 DatabaseReference ref = fbs.getDb()
			                .getReference(path);
				String message="$sendMe$"+sender+a+"\\"+filePath.substring(point+1);
				 System.out.println("Mesage:"+message);
				    
				    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				    LocalDateTime now = LocalDateTime.now();  
				    String timestamp=dtf.format(now);
				    
					ref.push().setValueAsync(new Message(timestamp,message,sender,a));
					System.out.println("Data pushed"+ref);
			}    
		
		    
		    
		}
		else {
			for(String a:frcver) {
				String path="/File/"+sender+"/Receive/"+a;
				 DatabaseReference ref = fbs.getDb()
			                .getReference(path);
				String message="$sendMe$";
				 System.out.println("Mesage:"+message);
				    
				    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				    LocalDateTime now = LocalDateTime.now();  
				    String timestamp=dtf.format(now);
				    
					ref.push().setValueAsync(new Message(timestamp,message,sender,a));
					System.out.println("Data pushed"+ref);
			}    
			
		    }
		    }		    catch(Exception eee) {}
		
	try {
		 document.insertString(document.getLength(),"\n"+sender+":-->"+filePath+": Sent",null);
	} catch (BadLocationException e) {
		e.printStackTrace();
	}
	System.out.println("File Sent! Thread closed");
	
	}
	
	public synchronized int sendImage(String filePath) {
		
	
		
	try {
		DataOutputStream st=new DataOutputStream(sc.getOutputStream());
		st.writeUTF("sendTo");
		st.flush();
		st.writeUTF(sender);
		st.flush();
		String s = (frcver.length==1)?frcver[0]:sender;
		st.writeUTF(s);
		st.flush();
		String fileName=filePath.substring(filePath.lastIndexOf('\\')+1);
		st.writeUTF(fileName);
		st.flush();
		sc.close();
		SocketChannel sc=SocketChannel.open();
		sc.connect(new InetSocketAddress(Variables.ip,199));
		System.out.println("fileName:"+fileName);
		String path=filePath;
		System.out.println("path:"+path);
		FileInputStream fis=new FileInputStream(path);
		FileChannel fc=fis.getChannel();
		System.out.println("Tranfer gonna start");
		ByteBuffer buffer=ByteBuffer.allocate(1024);
		
		while(true) {
			buffer.clear();
			int r=fc.read(buffer);
			if(r==-1) break;
			buffer.flip();
			sc.write(buffer);
		}
		System.out.println("File Tranfsered to Server");
		fis.close();
		sc.close();
		System.out.println("File sent");
	} catch (FileNotFoundException e) {
		return -1;
	} catch (IOException e) {
		return -1;
	}
	try {
		sc.close();
		return 0;
	} catch (IOException e) {
		return -1;
	}
	
	
	
	}

}

/*import java.awt.image.BufferedImage;
 
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class InitTransfer implements Runnable {
	private BlockingQueue<String> sendBuffer;
	InetAddress id;
	Socket sc;
	private String filePath;
	public InitTransfer(String path,BlockingQueue<String> q) {this.filePath=path;this.sendBuffer=q;}
	public void run() {
	try {
		 id=InetAddress.getByName("115.99.49.192");
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
	    sc=new Socket(id,99);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	int len=filePath.lastIndexOf('.');
	int point=filePath.lastIndexOf('\\');
	if(len==-1) return;
	else {
		
		if(filePath.substring(len+1).toLowerCase().equals("png") || filePath.substring(len+1).toLowerCase().equals("jpeg") || filePath.substring(len+1).toLowerCase().equals("jpg")) {
			int ret=sendImage(filePath);
			if(ret==-1) {
				JOptionPane.showMessageDialog(null,"Technical Error! File Send Failed");
			}
			else {
				JOptionPane.showMessageDialog(null,"File Sent");
			}
		}
		
	}
	try {
		if(point!=-1) {
		sendBuffer.put("$sendMe$"+filePath.substring(point+1));
		}
		else {
			sendBuffer.put("$SendMe$");
		}
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	System.out.println("File Send! Thread closed");
	
	}
	
	public int sendImage(String filePath) {
		
	
		
	BufferedImage image=null;
	try {
		DataOutputStream st=new DataOutputStream(sc.getOutputStream());
		st.writeUTF("sendTo");
		st.flush();
		st.writeUTF(filePath.substring(filePath.lastIndexOf('\\')+1));
		st.flush();
		image = ImageIO.read(new FileInputStream(filePath));
		ImageIO.write(image,"PNG", sc.getOutputStream());
		sc.getOutputStream().flush();
		System.out.println("File sent");
	} catch (FileNotFoundException e) {
		return -1;
	} catch (IOException e) {
		return -1;
	}
	try {
		sc.close();
		return 0;
	} catch (IOException e) {
		return -1;
	}
	
	
	
	}

}
*/