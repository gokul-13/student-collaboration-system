
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;

import javax.imageio.ImageIO;

import com.google.firebase.database.DatabaseReference;



public class InitReceive implements Runnable{
	
	FireBaseService fbs=null;
	BlockingQueue<String> recBuffer;
	private String admin;
	private String partner;
	private String recPath;
	String fileName;
	InetAddress inet;
	String key;
	DatabaseReference ref;
	BlockingQueue<String> sendBuffer;
	String ip;
	SocketChannel sch;
	
	public InitReceive(FireBaseService f,BlockingQueue<String> b,String n,String key,BlockingQueue<String> q,String a,String r,String pathy) {
		this.fbs=f;
		this.recBuffer=b;
		this.admin=a;
		this.partner=r;
		this.fileName=n;
		this.key=key;
		this.sendBuffer=q;
		this.ip=Variables.ip;
		this.recPath=pathy;
	}
	
	
	
	public void run() {
		try {
			inet=InetAddress.getByName(ip);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		Socket sc;
		try {
			sc = new Socket(inet,99);
			DataOutputStream s=new DataOutputStream(sc.getOutputStream());
			s.writeUTF("sendMe");
			s.flush();
			s.writeUTF(partner);
			s.flush();
			s.writeUTF(admin);
			s.flush();
			String fileNAME=fileName.substring(fileName.lastIndexOf('\\')+1);
			s.writeUTF(fileNAME);
			s.flush();
			String path="D:\\SEM_6\\rec\\"+admin;
			path+=fileNAME;
			sc.close();
		    sch=SocketChannel.open();
			sch.connect(new InetSocketAddress(ip,199));
			System.out.println("path:"+path);
			FileOutputStream fis=new FileOutputStream(path);
			FileChannel fc=fis.getChannel();
			System.out.println("Recive gonna start");
			ByteBuffer buffer=ByteBuffer.allocate(1024);
			
			while(true) {
				buffer.clear();
				int r=sch.read(buffer);
				if(r==-1) break;
				buffer.flip();
				fc.write(buffer);
			}
			fc.close();
			System.out.println("File Received From Server");
			fis.close();
			sch.close();
			System.out.println("File Received");
			
			
			
			try {
				System.out.println(path+"/"+key);
				recBuffer.put("File-Received Stored on:"+path+" ");
				ref=fbs.getDb().getReference(recPath);
		         ref.child(key).removeValue(null);
		         sendBuffer.put("File Transfer:"+fileNAME);
			} catch (InterruptedException e) {
				System.out.println("Interuppeted Exception");
				e.printStackTrace();
			}
		} catch (IOException e1) {
			System.out.println("IO Exception");
			e1.printStackTrace();
		}
		System.out.println("File Recived ! Thread closed");
	}
}



/*import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import com.google.firebase.database.*;


import java.io.IOException;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.firebase.database.DatabaseReference;



public class InitReceive implements Runnable{
	
	FireBaseService fbs=null;
	BlockingQueue<String> recBuffer;
	String recPath;
	String op;
	String fileName;
	InetAddress inet;
	String key;
	DatabaseReference ref;
	BlockingQueue<String> sendBuffer;
	String server_ip = "";
	
	public InitReceive(FireBaseService f,BlockingQueue<String> b,String p,String o,String n,String key,BlockingQueue<String> q) {
		this.fbs=f;
		this.recBuffer=b;
		this.recPath=p;
		this.op=o;
		this.fileName=n;
		this.key=key;
		this.sendBuffer=q;
	}
	
	
	
	public void run() {
		try {
		DatabaseReference ref = fbs.getDb()
                .getReference("/IP/");
        ref.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                String server_ip = dataSnapshot.getValue(String.class);
                System.out.println(server_ip);
                
            }


            public void onCancelled(DatabaseError error) {
                System.out.print("Error: " + error.getMessage());
            }
        });
		}
		catch(Exception e) {};
		try {
			inet=InetAddress.getByName("192.168.1.4");
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		Socket sc;
		try {
			sc = new Socket(inet,99);
			DataOutputStream s=new DataOutputStream(sc.getOutputStream());
			s.writeUTF(op);
			s.flush();
			s.writeUTF(fileName);
			s.flush();
			String path="D:\\SEM_6\\send";
			path+=fileName;
			BufferedImage buf=ImageIO.read(sc.getInputStream());
			if(buf==null) {System.out.println("Error! Occured");return;}
			FileOutputStream op=new FileOutputStream(path);
			ImageIO.write(buf,"PNG", op);
			op.flush();
			sc.close();
			try {
				recBuffer.put("File-Received Stored on:"+path+" ");
				ref=fbs.getDb().getReference(recPath);
		         ref.child(key).removeValue(null);
		         sendBuffer.put("File Transfer:"+fileName);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("File Recived ! Thread closed");
	}
}
*/