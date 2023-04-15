
public class Message {

	public String message;
    public String timestamp;
    public String sender;
    public String receiver;
	public Message(String timestamp,String message,String s,String r) {
		super();
		this.message = message;
		this.timestamp=timestamp;
		this.sender = s;
		this.receiver=r;
	}
	public Message() {
		super();
	}
	
	
}
/*import java.sql.Timestamp;  

public class Message {

	public String message;
	public Timestamp timestamp;
	public String sender;
	public String recver;
	public Message(String message, Timestamp timestamp, String sender, String recver) {
		super();
		this.message = message;
		this.timestamp = timestamp;
		this.sender = sender;
		this.recver = recver;
	}
	public Message() {
		super();
	}
	
	
	
}*/
