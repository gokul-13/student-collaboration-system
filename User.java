
import java.io.*;
import java.util.*;
import com.google.firebase.database.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//import com.google.firebase.database.DatabaseReference;
public class User implements Serializable{
	
	public String userName,firstName,lastName,mobile,email,password;
	public ArrayList<String> groups;

	public User(String userName, String firstName, String lastName, String mobile, String email,String password) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobile = mobile;
		this.email = email;
		this.password = password;
		this.groups=new ArrayList<String>();
		this.groups.add("**SELECT GROUP**");
	}
	
	public User () {super();this.groups=new ArrayList<String>();
	this.groups.add("**SELECT GROUP**");}
	
	public static User getUser(String u1) {
		User u = new User();
		try {
			Connection c = null;
			
			 Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection(Variables.host,
	            Variables.username, Variables.passkey);
	         
		Statement stmt = c.createStatement();
		String username = u1;
		
	    String sql = "SELECT * FROM user_details WHERE username = '" + username+"';";


	   ResultSet resultSet = stmt.executeQuery(sql);

	        if (resultSet.next()) {
	        	u.userName=resultSet.getString("username");
	        	u.firstName=resultSet.getString("firstname");
	        	u.lastName=resultSet.getString("lastname");
	        	u.mobile=resultSet.getString("mobile");
	        	u.email=resultSet.getString("email");
	            u.password = resultSet.getString("password");  
	        }
	        else {
	        	System.out.println("RETRY2!!!!");

	        }
	        }
		catch (SQLException ex) {  
	    	System.out.println("RETRY3!!!!"+ex);

	    }
		catch (Exception ee) {
	    	System.out.println("RETRY4!!!!"+ee);

		}
		Connection c = null;
		
		try {
			
			 Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection(Variables.host,
	            Variables.username, Variables.passkey);
	         
		Statement stmt = c.createStatement();
		String username = u1;
		
	    String sql = "SELECT * FROM groups WHERE username = '" + username+"';";


	   ResultSet resultSet = stmt.executeQuery(sql);
	   String t;
	        while (resultSet.next()) {
	        	t=resultSet.getString("groupname");	        
	        	u.groups.add(t);
	        	System.out.println(resultSet);
	        }
	        c.close();
	        }
		catch (SQLException ex) {  
	    	System.out.println("RETRY3!!!!"+ex);

	    }
		catch (Exception ee) {
	    	System.out.println("RETRY4!!!!"+ee);

		}
		
	 return u;
	   
	    
		
	}
	
	public User createUser() {
		String userName,firstName,lastName,mobile,email,password;
		Scanner s = new Scanner(System.in);
		userName = s.next();
		firstName = s.next();
		lastName = s.next();
		mobile = s.next();
		email = s.next();
		password = s.next();
			
		User u1 = new User(userName,firstName,lastName,mobile,email,password);
		return u1;
		
	}
	
	public Group CreateGroup(String name) {
		Group g = new Group(this,name);
		return g;
	}
	
}
/*{
"type": "service_account",
"project_id": "stugether-790b4",
"private_key_id": "6a955c05569873fe8da8248457ce7373a542a251",
"private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDxqRuckZjiP/lo\n0UBZHhyahW1eNTWHw0DpkNByEU/k6Ok8dLO6NvWKq8m60GMJcA0Gb/DYsAh6AbO7\nYGiUha2GnEMWvrZuHmLEZGTggSbV4BsrZc6j4ndB3vVRxKnGQhDHSlNmkDIgXqg1\noXZSvLKLmNLiekrdngguyk2EKu4FVvdCdCwAzULOxg9VSNYYNNsSPqOfSUgt4qI5\n2yv6pGdcmEa2q6rFjRwikBjFlC5jOPHOXm3L3nxJVlvGqsUA2TlT7Qj02GYcgOZ4\nzJaKEQoNWJqGssS20MHJbNlVpzWW3FygL/57X4brR/HSFe4HtPlOrQSfq775az6+\nLdpRMomvAgMBAAECggEAAaSya7R7GKwcsxTGiOnxLIUpwuaTuvJIomNPYjyHPDma\nlG8AEI0dIFieDOeBNOg5KR5hFz14i/PaWAowy98Y2Kh+taHjCwlT3J1eKOoCl0O9\n4OQBWBCghPx800CGHbNcpS5L4OUBd5eNMfLWI3vlru4Bc8l8cYNd74uFTsJypJX5\nGGvThDYl3meNFgw3lzcaTYgS3htvokgUQJugA+v090B/OSEw1/+EmIG23HJ7Eh+v\nYx6Mz7qD8akhAjqWmoeuWWS6SPTRayB+dlXL29mexSJ40A9PYUlm+Ql6krFpBSuc\nT9Ewy1iI9UWlP0m33NKrNuRwboJRscJ4Jrf/+BotmQKBgQD7Ti9NEdsqh2xZoSb2\noN4Jd+r4UZSZv5OTaeRdGd656eVNfQpSoJSFaBWwKJvJ3KpnrzVfb6zmdRQUw0BU\ns+okvq1Oo7GtrGanhvGOH8n50piuR4PgiEDPRxl2nppjLcmA4GnSIfkyYW1p5fmJ\nh5vQt4+7fOtNlLh6C5zeH3E9xQKBgQD2LMx4SCxyuEUhu877LEQjw3gC5Pyh9LJR\nPfC0twwDTLaHtFGD4iFqGdhm/RXGBXPSnGuJgkz2bA4x7+ztpc43Y2vzjr8cCSPd\nMzDknwpknl5NNbr9HRJiE8cRbn3Dt2grihB/I/sfOm2qzwbcb1OJWFGQM76aUswY\nEmhhbNX04wKBgQDSUlgSF2ymxlovFUlnxbCumfdERfqpRoW7LTebl6gpTpDDbm/y\nQUSr9jWYjhP8OLtBNYjky7EX20qoKdUwTrcac57WHvf4Uz5C3RRTD1XZyKBR07aS\nd1ZOY8Hy3cB2Wv625pqJ7DcELDgHFhrnOGB2qPoZPu4rqtlOA2QRHp2GxQKBgQCR\nAdp/j6P+/2RZdpN5iJ2qo11x9wiJLeZLzLyY9qnw16M9VI4frXgZU5gRMks9FcEf\n8lu16DcFgKjr6XQamGxcXAges5sHHKsvhRPr13L7yf2qZVf7+L/3XccJEZirTMHj\nw7Pr8Hnf7z7iOojEGB+Q66E/2/sOVG88TruFh+KQGQKBgQCBWkON8Vy1iAVMV2bf\n46fxTcbs0weiuoU5v+UElAwMv/I2RycFdA4Oq0xM1IHwaKT6CJAZGJu0OrVOjlq7\nKCLF11zVknCX54WJzkQnyjMGQdAL3gHXKWwrlBV7s5HKTWvPTJwY0GcayB1CR4e1\nmOPBvyFsKyvMQlv1dXHXX4MxWQ==\n-----END PRIVATE KEY-----\n",
"client_email": "firebase-adminsdk-bqsb1@stugether-790b4.iam.gserviceaccount.com",
"client_id": "117343232416633251535",
"auth_uri": "https://accounts.google.com/o/oauth2/auth",
"token_uri": "https://oauth2.googleapis.com/token",
"auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
"client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-bqsb1%40stugether-790b4.iam.gserviceaccount.com"
}*/