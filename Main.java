
import com.google.firebase.database.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import com.google.firebase.database.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import java.io.IOException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
    	try {
			main_page l= new main_page();
			Thread t1=new Thread(l);
        t1.start();
        	
			
		
        Thread.currentThread().wait();
       
        t1.join();
       System.out.println("JOINED!!");
        
		
		
        Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n\nWorked!!");
    }
		
}
/* class Signup1 implements Runnable {
User u1;
boolean b=false; 
public void run() {
Connection c = null;
 
 
 Scanner s = new Scanner(System.in);
 
 
 String userName="",firstName="",lastName="",mobile="",email="",password="";      
try {
	userName = s.next();
	firstName = s.next();
	lastName = s.next();
	mobile = s.next();
	email = s.next();
	password = s.next();
	u1 = new User(userName,firstName,lastName,mobile,email,password);
	b=true;
	}
	catch(Exception e) {
		System.out.println("RETRY INPUT!!");
		b=false;
	}
if(b){		
try{
	 Class.forName("org.postgresql.Driver");
    c = DriverManager
       .getConnection("jdbc:postgresql://localhost:5432/stugether",
       "postgres", "dhanu26");
    
Statement stmt = c.createStatement();

String sql = "INSERT INTO user_details (userName,firstName,lastName,mobile,email,password) "
  + "VALUES ('"+userName+"','"+firstName+"','"+lastName+"','"+mobile+"','"+email+"','"+password+"');";
stmt.executeUpdate(sql);

c.commit();

System.out.println(" SIGNUP COMPLETE ");
b=true;

}
catch(Exception e) {
	System.out.println(e);
	b=false;
}
}

}  
}

class Login implements Runnable {

String username,password;
User u =new User();
boolean login;

Login(String u,String p){
username=u;
password = p;

}


public void run() {
Connection c = null;
 
 

String usern,firstName,lastName,mobile,email,pass;
	
	
try {
	
	 Class.forName("org.postgresql.Driver");
    c = DriverManager
       .getConnection("jdbc:postgresql://localhost:5432/stugether",
       "postgres", "dhanu26");
    
Statement stmt = c.createStatement();
String sql = "SELECT * FROM user_details WHERE username = '" + username+"';";


ResultSet resultSet = stmt.executeQuery(sql);

   if (resultSet.next()) {
   	u.userName=resultSet.getString("username");
   	u.firstName=resultSet.getString("firstname");
   	u.lastName=resultSet.getString("lastname");
   	u.mobile=resultSet.getString("mobile");
   	u.email=resultSet.getString("email");
       u.password = resultSet.getString("password");
       
       if(u.password.equals(password)) {
       	System.out.println("LOGED IN!!!!");
       	login=true;
   }  
       else {
       	System.out.println("RETRY!!!!");
       }
   }
   else {
   	System.out.println("RETRY2!!!!");

   }
   }
catch (SQLException ex) {  
	System.out.println("RETRY3!!!!"+ex);

}
catch (Exception e) {
	System.out.println("RETRY4!!!!"+e);

}
System.out.println(" LOGIN COMPLETE ");

}

}  
*/

/*class Signup implements Runnable {
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

DatabaseReference usersRef = ref;

Scanner s = new Scanner(System.in);
System.out.println("Enter Details :");
String userName,firstName,lastName,mobile,email,password;

userName = s.next();
//Check User-name
firstName = s.next();
lastName = s.next();
mobile = s.next();
email = s.next();
password = s.next();
	
User u1 = new User(userName,firstName,lastName,mobile,email,password);

usersRef.child(userName).setValueAsync(u1);
System.out.println("Sign Up Successfull!");

ref.addValueEventListener(new ValueEventListener() {

public void onDataChange(DataSnapshot dataSnapshot) {
            User u = dataSnapshot.getValue(User.class);
            System.out.println(u);
    }

public void onCancelled(DatabaseError error) {
    		System.out.print("Error: " + error.getMessage());
        }
	});


}
}

class Login implements Runnable {
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

DatabaseReference usersRef = ref;

Scanner s = new Scanner(System.in);
System.out.println("Enter Details :");
String userName,firstName,lastName,mobile,email,password;

//userName = s.next();

ref.addListenerForSingleValueEvent(new ValueEventListener() {
	 public void onDataChange(DataSnapshot dataSnapshot) {
         Object u = dataSnapshot.getValue();
         System.out.println(u);
         
 }

public void onCancelled(DatabaseError error) {
 		System.out.print("Error: " + error.getMessage());
     }
	});

//ref.addValueEventListener(new ValueEventListener() {
/*  
public void onDataChange(DataSnapshot dataSnapshot) {
            User u = dataSnapshot.getValue(User.class);
            System.out.println(u);
    }

public void onCancelled(DatabaseError error) {
    		System.out.print("Error: " + error.getMessage());
        }
	});
}
}*/