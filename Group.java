import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.firebase.database.*;

public class Group implements Runnable {
	User admin;
	User[] participants;
	public String name;
	String description;
	
	public Group(User admin, String name, String description) {
		super();
		this.admin = admin;
		this.name = name;
		this.description = description;
	}
	public Group(User admin, String name) {
		super();
		this.admin = admin;
		this.name = name;
	}
	
	public boolean addParticipant(User u1) {
	    FireBaseService fbs=null;
		try {
	    	
	    	try {
            fbs = new FireBaseService();
        } 
    catch (IOException e) {
            e.printStackTrace();
        }
		    DatabaseReference ref = fbs.getDb()
		                .getReference("/Group/"+name+"/Participants/");
		    DatabaseReference usersRef = ref;
		    	
			usersRef.push().setValueAsync(u1.userName);
			System.out.println("Data pushed"+usersRef);
			usersRef.addValueEventListener(new ValueEventListener() {

	            public void onDataChange(DataSnapshot dataSnapshot) {
	                Iterable<DataSnapshot> document = dataSnapshot.getChildren();
	                for (DataSnapshot child : document) {
	                    
	                
	                System.out.println(child.getValue());}
	            }


	            public void onCancelled(DatabaseError error) {
	                System.out.print("Error: " + error.getMessage());
	            }
	        });
		    
		    }catch(Exception e) {
		    	System.out.println(e);
		    }
		
		
		
	    return true;}
	
	
	
	
		/*FireBaseService fbs = null;
	    try {
	            fbs = new FireBaseService();
	        } 
	    catch (IOException e) {
	            e.printStackTrace();
	        }
	    
	    DatabaseReference ref = fbs.getDb().getReference("/Group/"+name+"/Participants/");
	    
	   ref.child("u1").push().setValueAsync("String");
	   System.out.println("END"+ref);
		
		return true;
	}
	*/
	public void run() {
		 
		 	User u1 = User.getUser("dhanu26");

		Group g = new Group(u1,"Hello");
		System.out.println(u1.firstName);
		g.addParticipant(u1);
		
	}
	
	
	

}
