import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import com.google.firebase.database.*;

public class SendMessage implements Runnable{
	 FireBaseService fbs = null;User u1;String user1,user2,receiver;String g;int type;
	  private BlockingQueue<String> sendBuffer=null;
	  //private BlockingQueue<String> userbuff=null;
	  public SendMessage(BlockingQueue<String> name, FireBaseService fbs,User u,int t) {this.type=t; this.u1=u;this.sendBuffer=name;this.fbs=fbs;}
	  public SendMessage(BlockingQueue<String> name, FireBaseService fbs,User user,String g,int t) {this.u1=user; this.type=t; this.g=g;this.sendBuffer=name;this.fbs=fbs;}
	public void run() {
	     if(type==1) {
	    try{
	    	
	    	String temp=u1.userName;
	    	user1=sendBuffer.take();
	        receiver = user1;
	         int cmp = user1.compareToIgnoreCase(temp);
	         if(cmp==0) {
	        	 throw new Exception();
	         }
	         else if(cmp>0) {
	        	 user2=temp;
	         }
	         else {
	        	 user2 = user1;
	        	 user1=temp;
	         }
	         String path="/Messages/Conversation/"+user1+"and"+user2+"/";
	    DatabaseReference ref = fbs.getDb()
	                .getReference(path);
	    DatabaseReference usersRef = ref;
	    while(true) {
	    String message=sendBuffer.take();
	    System.out.println("Mesage:"+message);
	    
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	    LocalDateTime now = LocalDateTime.now();  
	    String timestamp=dtf.format(now);
	    
		usersRef.push().setValueAsync(new Message(timestamp,message,u1.userName,receiver));
		System.out.println("Data pushed"+usersRef);
		
	    }
	    }catch(InterruptedException ex) {return;}
	    catch(Exception eee) {}
	     }
	     if(type==2) {
	    	 try {
	    	String path="/Group/"+g+"/Messages/";
	    	 
	 	    DatabaseReference ref = fbs.getDb()
	 	                .getReference(path);
	 	    
	 	   while(true) {
	 		    String message=sendBuffer.take();
	 		    System.out.println("Mesage:"+message);
	 		    
	 		    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	 		    LocalDateTime now = LocalDateTime.now();  
	 		    String timestamp=dtf.format(now);
	 		    
	 			ref.push().setValueAsync(new Message(timestamp,message,u1.userName,"group"));
	 			System.out.println("Data pushed"+ref);
	 			
	 		    }
	 		    }catch(InterruptedException ex1) {return;}
	 		    catch(Exception eee1) {}
	 		     }
	     }
	}

	

/*import java.io.IOException;
import java.util.Scanner;

import com.google.firebase.database.*;

public class SendMessage implements Runnable{
	
	Message message=new Message();
	public void run() {
		Scanner s = new Scanner(System.in);
		String sender,rcver,data;
		
		System.out.println("FromuserID:");
		sender = s.next();
		message.sender=sender; 
		System.out.println("TouserID:");
		rcver = s.next();message.recver=rcver;
		System.out.println("Data:");
		data=s.nextLine();message.message=data;
		
		
		FireBaseService fbs = null;
	    try {
	            fbs = new FireBaseService();
	        } 
	    catch (IOException e) {
	            e.printStackTrace();
	        }
	    
	    DatabaseReference ref = fbs.getDb()
	                .getReference("/Message/Sentto");
	   // DatabaseReference ref1 = fbs.getDb()
         //       .getReference("/Message/Rcv/");
	    
	    DatabaseReference usersRef = ref;
		
		usersRef.child(rcver).setValueAsync(message);

	}


	
}
*/