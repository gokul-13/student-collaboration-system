

import com.google.firebase.database.*;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ShowDbChanges extends Thread {
	private BlockingQueue<String> sendBuffer=null;
	private BlockingQueue<String> recBuffer=null;
	private BlockingQueue<String> userBuffer=null;
	private  FireBaseService fbs = null;
	private  String path;
	DatabaseReference ref,ref2,ref3;
	String user1="",user2="";
	User u ; String sendPath;
	String val,rcver,recPath;
	int type;
	String g;
	public ShowDbChanges() {}
	public ShowDbChanges(BlockingQueue<String> q,BlockingQueue<String> q1,BlockingQueue<String> ub, FireBaseService fbs,User user,int type) {this.type=type;this.u=user;this.sendBuffer=q;this.recBuffer=q1;this.userBuffer=ub;this.val=val;this.fbs=fbs;}
	public ShowDbChanges(BlockingQueue<String> q,BlockingQueue<String> q1,BlockingQueue<String> ub, FireBaseService fbs,User user,String g,int type) {this.g=g; this.type=type;this.u=user;this.sendBuffer=q;this.recBuffer=q1;this.userBuffer=ub;this.val=val;this.fbs=fbs;}

    public void run() {
  if(type==1) {
    String temp = u.userName;    
         try {
         System.out.println("Waitimg for user");
         user1=userBuffer.take();
         rcver =user1;
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
        recPath="/File/"+u.userName+"/Receive/"+rcver;
        System.out.println(recPath);
         System.out.println(user1+"  "+user2);
        }catch(InterruptedException ex) {return;}catch(Exception eee) {
	    	return;
	    }
        path="/Messages/Conversation/"+user1+"and"+user2+"/";
        System.out.println(path);
       
        ref = fbs.getDb()
                .getReference(path);
        ref2 = fbs.getDb()
                .getReference(recPath);
        
        
        ref.addChildEventListener(new ChildEventListener() {
        	 
        	  public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
        		String key=dataSnapshot.getKey();
        		System.out.println("Current_Key:"+key);
        	    Message newPost = dataSnapshot.getValue(Message.class);
        	    System.out.println(newPost.message);
        	    
        	    
        	    try {
        	    recBuffer.put(newPost.sender+":-->"+newPost.message);
        	    }
        	    catch(InterruptedException ex)
        	    {return;}
        	    
        	    
        	    
        	    System.out.println("Previous Post ID: " + prevChildKey);
        	  }
        	 
   
        	  public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

        	 
        	  public void onChildRemoved(DataSnapshot dataSnapshot) {}

        	  
        	  public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

        	  public void onCancelled(DatabaseError databaseError) {}
        });
        
        ref2.addChildEventListener(new ChildEventListener() {
       	 
      	    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
      		String key=dataSnapshot.getKey();
      		System.out.println("\n\nREFER2\n\nCurrent_Key:"+key);
      	    Message newPost = dataSnapshot.getValue(Message.class);
      	    System.out.println(newPost.message);
      	    
      	    
      	    	String op = newPost.message.substring(newPost.message.indexOf("$")+1,newPost.message.lastIndexOf("$"));
      	    	String fileName=newPost.message.substring(newPost.message.lastIndexOf("$")+1);
      	    	Thread t=new Thread(new InitReceive(fbs,recBuffer,fileName,key,sendBuffer,u.userName,rcver,recPath));
      	    	t.start();
      	    	try {
						recBuffer.put(rcver+":-->"+"A file will be received "+fileName);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
      	    
      	    System.out.println("Previous Post ID: " + prevChildKey);
      	  }
      	public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

   	 
  	  public void onChildRemoved(DataSnapshot dataSnapshot) {}

  	  
  	  public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

  	  public void onCancelled(DatabaseError databaseError) {}
      	  });

      	 
   
 
  }	
  if(type==2) {
	  try {
	  recPath="/File/"+g+"/Receive/"+u.userName;
      System.out.println(recPath);
      
      }
      catch(Exception eee) {
	    	return;
	    }
      path="/Group/"+g+"/Messages/";
      System.out.println(path);
     
      ref = fbs.getDb()
              .getReference(path);
      ref2 = fbs.getDb()
              .getReference(recPath);
      
      
      ref.addChildEventListener(new ChildEventListener() {
      	 
      	  public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
      		String key=dataSnapshot.getKey();
      		System.out.println("Current_Key:"+key);
      	    Message newPost = dataSnapshot.getValue(Message.class);
      	    System.out.println(newPost.message);
      	    
      	    
      	    try {
      	    recBuffer.put(newPost.sender+":-->"+newPost.message);
      	    }
      	    catch(InterruptedException ex)
      	    {return;}
      	    
      	    
      	    
      	    System.out.println("Previous Post ID: " + prevChildKey);
      	  }
      	 
 
      	  public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

      	 
      	  public void onChildRemoved(DataSnapshot dataSnapshot) {}

      	  
      	  public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

      	  public void onCancelled(DatabaseError databaseError) {}
      });
      
    ref2.addChildEventListener(new ChildEventListener() {
        	 
      	  public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
      		String key=dataSnapshot.getKey();
      		System.out.println("Current_Key:"+key);
      	    Message newPost = dataSnapshot.getValue(Message.class);
      	    System.out.println(newPost.message);
      	    
      	    
      	    	String op = newPost.message.substring(newPost.message.indexOf("$")+1,newPost.message.lastIndexOf("$"));
      	    	String fileName=newPost.message.substring(newPost.message.lastIndexOf("$")+1);
      	    	Thread t=new Thread(new InitReceive(fbs,recBuffer,fileName,key,sendBuffer,g,u.userName,recPath));
      	    	t.start();
      	    	try {
						recBuffer.put(rcver+":-->"+"A file will be received "+"fileName:");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
      	    
      	    System.out.println("Previous Post ID: " + prevChildKey);
      	  }
      	public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

   	 
  	  public void onChildRemoved(DataSnapshot dataSnapshot) {}

  	  
  	  public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

  	  public void onCancelled(DatabaseError databaseError) {}
      	  });

  }
    }
    
    }



/*import com.google.firebase.database.*;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ShowDbChanges extends Thread {
	private BlockingQueue<String> sendBuffer=null;
	private BlockingQueue<String> recBuffer=null;
	private BlockingQueue<String> userBuffer=null;
	private  FireBaseService fbs = null;User u1;
	DatabaseReference ref;
	String user="";
	String val;
	public ShowDbChanges() {}
	public ShowDbChanges(BlockingQueue<String> q,BlockingQueue<String> q1,BlockingQueue<String> ub, FireBaseService fbs,User u) {this.u1=u;this.sendBuffer=q;this.recBuffer=q1;this.userBuffer=ub;this.val=val;this.fbs=fbs;}
	
    public void run() {
  
   
       
         try {
         System.out.println("Waitimg for user");
         user=userBuffer.take();
         System.out.println(user);
        }catch(InterruptedException ex) {}
        String recPath="/"+u1.userName+"/"+"Receive/"+user;
        System.out.println(recPath);
       
        ref = fbs.getDb()
                .getReference(recPath);
        ref.addChildEventListener(new ChildEventListener() {
        	 
        	  public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
        		
        	    Message newPost = dataSnapshot.getValue(Message.class);
        	    System.out.println(newPost.message);
        	    try {
        	    recBuffer.put(user+":-->"+newPost.message);
        	    }catch(InterruptedException ex) {return;}
        	    System.out.println("Previous Post ID: " + prevChildKey);
        	  }
        	 
   
        	  public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

        	 
        	  public void onChildRemoved(DataSnapshot dataSnapshot) {}

        	  
        	  public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

        	  public void onCancelled(DatabaseError databaseError) {}
        	});
        

    }
}




/*import com.google.firebase.database.*;

import java.io.IOException;

public class ShowDbChanges implements Runnable {
    public void run() {
        FireBaseService fbs = null;
        try {
            fbs = new FireBaseService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatabaseReference ref = fbs.getDb()
                .getReference("/");
        ref.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                Object document = dataSnapshot.getValue();
                System.out.println(document);
            }


            public void onCancelled(DatabaseError error) {
                System.out.print("Error: " + error.getMessage());
            }
        });


    }
}
*/