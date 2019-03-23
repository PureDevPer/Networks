import javax.swing.JFrame;

public class TCPstalkc 
{
	public static void main(String[] args) 
	{
		String desthost = "localhost";
		
		Client app = new Client(desthost);
		
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.runClient();
	}
}
