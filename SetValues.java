import com.google.firebase.database.*;
import java.io.IOException;
import java.util.*;

public class SetValues implements Runnable {
	public void run() {
		FireBaseService fbs = null;
    try {
            fbs = new FireBaseService();
        } 
    catch (IOException e) {
            e.printStackTrace();
        }
    
    DatabaseReference ref = fbs.getDb()
                .getReference("/User/");  
    
    
    
    DatabaseReference usersRef = ref;//.child("users");
    
    Scanner s = new Scanner(System.in);
	System.out.println("ENTER:");
	String userName,firstName,lastName,mobile,email,password;
	
	userName = s.next();
	firstName = s.next();
	lastName = s.next();
	mobile = s.next();
	email = s.next();
	password = s.next();
		
	User u1 = new User(userName,firstName,lastName,mobile,email,password);
	
	usersRef.child(userName).setValueAsync(u1);
    ref.addValueEventListener(new ValueEventListener() {
    
    public void onDataChange(DataSnapshot dataSnapshot) {
                Object document = dataSnapshot.getValue();
                System.out.println(document);
        }
    
    public void onCancelled(DatabaseError error) {
        		System.out.print("Error: " + error.getMessage());
            }
    	});
    
    u1.userName="hi";
    //u1.getUserDetails(userName);
    
    }
}